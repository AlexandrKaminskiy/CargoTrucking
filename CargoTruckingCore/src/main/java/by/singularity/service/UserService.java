package by.singularity.service;

import by.singularity.dto.UserDto;
import by.singularity.entity.*;
import by.singularity.exception.UserException;
import by.singularity.mapper.impl.UserMapper;
import by.singularity.pojo.EmailChanger;
import by.singularity.pojo.PasswordChanger;
import by.singularity.repository.InvoiceRepository;
import by.singularity.repository.RepairingMailRepository;
import by.singularity.repository.UserRepository;
import by.singularity.repository.WayBillRepository;
import by.singularity.repository.queryUtils.QPredicate;
import by.singularity.service.utils.ParseUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;
    private final WayBillRepository wayBillRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RepairingMailRepository repairingMailRepository;

    @Value("${jwt.secret}")
    private String secret;

    public Optional<User> findUser(String login) {
        return userRepository.findOne(getLoginPredicate(login));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOne(getLoginPredicate(username))
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        log.info("USER {} WAS FOUND", user.getName());
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoles().forEach((c)->grantedAuthorities.add(new SimpleGrantedAuthority(c.toString())));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthorities);
    }

    @Transactional
    public User registerUser(UserDto userDto) throws UserException {
        User user = userMapper.toModel(userDto);
        if (userRepository.exists(getLoginPredicate(userDto.getLogin()))) {
            log.info("user with username {} exists!",user.getLogin());
            throw new UserException("user with username " + user.getLogin() + " exists");
        }
        if (userRepository.exists(getEmailPredicate(userDto.getEmail()))) {
            log.info("user with email {} exists!",user.getEmail());
            throw new UserException("user with email " + user.getEmail() + " exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("user {} saved",user.getLogin());
        return user;
    }

    @Transactional
    public void updateUser(UserDto userDto, Long id) throws UserException{
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserException("user with id " + id + " doesn't exist"));
        alterUserProfileInfo(userDto,user);
        alterFromAdminInfo(userDto,user);
        userRepository.save(user);
        log.info("USER {} UPDATED", user.getLogin());
    }

    public Page<User> getAllUsers(Pageable pageable, Map<String,String> params) {
        return userRepository.findAll(getSearchPredicate(params), pageable);
    }

    @Transactional
    public void deleteUsers(Long id) throws UserException {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserException("user with id " + id +" not found"));
        List<Invoice> invoices = user.getInvoice();
        invoices.forEach(invoice -> {
            if (invoice.getDriver().getId().equals(id)) {
                invoice.setDriver(null);
            }
            if (invoice.getCreator().getId().equals(id)) {
                invoice.setCreator(null);
            }
            invoiceRepository.save(invoice);
        });
        List<WayBill> wayBills = user.getWayBill();
        wayBills.forEach(wayBill -> {
            wayBill.setVerifier(null);
            wayBillRepository.save(wayBill);
        });
        userRepository.delete(user);
    }

    public User getById(Long id) throws UserException {
        return userRepository.findById(id)
                .orElseThrow(()->new UserException("user with id " + id + " not found"));
    }

    public UserDto getUserAuthInfo(HttpServletRequest request) throws UserException {
        User user = getUserByAuthorization(request);
        log.info("USER WITH ID {} UPDATED",user.getId());
        return userMapper.toDto(user);
    }

    public void alterUserAuthInfo(HttpServletRequest request, UserDto userDto) throws UserException{
        User user = getUserByAuthorization(request);
        alterUserProfileInfo(userDto,user);
        userRepository.save(user);
        log.info("USER WITH ID {} DELETED",user.getId());
    }

    @Transactional
    public void changePassword(HttpServletRequest request, PasswordChanger changer) throws UserException{
        User user = getUserByAuthorization(request);
        if (passwordEncoder.matches(changer.getOldPassword(),user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changer.getNewPassword()));
            userRepository.save(user);
            log.info("PASSWORD CHANGED");
        }
    }

    private String getLogin(String authHeader) {
        String token = authHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();
    }

    public User getUserByAuthorization(HttpServletRequest request) throws UserException {
        String authHeader = request.getHeader(AUTHORIZATION);
        String login = getLogin(authHeader);
        return userRepository.findOne(getLoginPredicate(login))
                .orElseThrow(()->new UserException("user not found"));
    }

    @Transactional
    public String restorePassword(String uuid, String password) throws UserException {
        RepairingMessage repairingMessage = repairingMailRepository.findByUuid(uuid)
                .orElseThrow(()->new UserException("check your uuid"));
        if (repairingMessage.getExpirationTime() < System.currentTimeMillis()) {
            throw new UserException("uuid expired");
        }
        User user = userRepository.findOne(getEmailPredicate(repairingMessage.getEmail()))
                .orElseThrow(()->new UserException("check your uuid"));
        if (password.length() < 5 || password.length() > 72) {
            throw new UserException("your password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        repairingMailRepository.delete(repairingMessage);
        log.info("PASSWORD CHANGED");
        return "password changed";
    }

    public RepairingMessage getEmailChanger(EmailChanger emailChanger, HttpServletRequest request) throws UserException {
        User user = getUserByAuthorization(request);
        if (!passwordEncoder.matches(emailChanger.getPassword(), user.getPassword())) {
            throw new UserException("incorrect password");
        }
        if (userRepository.exists(getEmailPredicate(emailChanger.getRecipient()))) {
            throw new UserException("email " + emailChanger.getRecipient() + " just exists");
        }
        RepairingMessage repairingMessage = new RepairingMessage();
        repairingMessage.setUuid(UUID.randomUUID().toString());
        repairingMessage.setEmail(emailChanger.getRecipient());
        repairingMessage.setExpirationTime(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
        repairingMessage.setOldEmail(user.getEmail());
        return repairingMessage;
    }

    public String activateEmail(String uuid) throws UserException {
        RepairingMessage repairingMessage = repairingMailRepository.findByUuid(uuid)
                .orElseThrow(()->new UserException("check your uuid"));
        if (repairingMessage.getExpirationTime() < System.currentTimeMillis()) {
            throw new UserException("uuid expired");
        }
        if (userRepository.exists(getEmailPredicate(repairingMessage.getEmail()))) {
            throw new UserException("email " + repairingMessage.getEmail() + " just exists");
        }
        User user = userRepository.findOne(getEmailPredicate(repairingMessage.getOldEmail()))
                .orElseThrow(()->new UserException("check your uuid"));
        user.setEmail(repairingMessage.getEmail());
        userRepository.save(user);
        repairingMailRepository.delete(repairingMessage);
        log.info("EMAIL CHANGED");
        return "Your email was successfully changed";
    }

    private Predicate getLoginPredicate(String login) {
        return QPredicate.builder()
                .add(login, QUser.user.login::eq)
                .buildAnd();
    }
    private Predicate getEmailPredicate(String email) {
        return QPredicate.builder()
                .add(email, QUser.user.email::eq)
                .buildAnd();
    }

    private Predicate getSearchPredicate(Map<String, String> params) {
        return QPredicate.builder()
                .add(params.get("name"), QUser.user.name::eq)
                .add(params.get("surname"), QUser.user.surname::eq)
                .add(params.get("patronymic"), QUser.user.patronymic::eq)
                .add(ParseUtils.parseDate(params.get("beforeBornDate")), QUser.user.bornDate::goe)
                .add(ParseUtils.parseDate(params.get("afterBornDate")), QUser.user.bornDate::loe)
                .add(params.get("town"), QUser.user.town::eq)
                .add(params.get("street"), QUser.user.street::eq)
                .add(ParseUtils.parseInt(params.get("house")), QUser.user.house::eq)
                .add(ParseUtils.parseInt(params.get("flat")), QUser.user.flat::eq)
                .add(ParseUtils.parseEnum(params.get("roles"), Role.class), QUser.user.roles::contains)
                .buildAnd();
    }

    private void alterUserProfileInfo(UserDto userDto, User user) {
        Optional.ofNullable(userDto.getName()).ifPresent(user::setName);
        Optional.ofNullable(userDto.getSurname()).ifPresent(user::setSurname);
        Optional.ofNullable(userDto.getPatronymic()).ifPresent(user::setPatronymic);
        Optional.ofNullable(userDto.getBornDate()).ifPresent(user::setBornDate);
        Optional.ofNullable(userDto.getTown()).ifPresent(user::setTown);
        Optional.ofNullable(userDto.getStreet()).ifPresent(user::setStreet);
        Optional.ofNullable(userDto.getFlat()).ifPresent(user::setFlat);
        Optional.ofNullable(userDto.getPassportNum()).ifPresent(user::setPassportNum);
        Optional.ofNullable(userDto.getIssuedBy()).ifPresent(user::setIssuedBy);
    }

    private void alterFromAdminInfo(UserDto userDto, User user) {
        Optional.ofNullable(userDto.getRoles()).ifPresent(user::setRoles);
    }
}
