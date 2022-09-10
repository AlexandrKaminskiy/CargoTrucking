package by.singularity.service;

import by.singularity.dto.UserDto;
import by.singularity.entity.QUser;
import by.singularity.entity.User;
import by.singularity.exception.UserException;
import by.singularity.mapper.impl.UserMapperImpl;
import by.singularity.pojo.PasswordChanger;
import by.singularity.repository.UserRepository;
import by.singularity.repository.queryUtils.QPredicate;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapperImpl userMapper;
    @Value("${jwt.secret}")
    private String secret;

    public Optional<User> findUser(String login) {
        return userRepository.findOne(getLoginPredicate(login));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findOne(getLoginPredicate(username));
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = userOpt.get();
        log.info("USER {} WAS FOUND", user.getName());
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoles().forEach((c)->grantedAuthorities.add(new SimpleGrantedAuthority(c.toString())));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthorities);

    }

    public User registerUser(UserDto userDto) throws UserException {
        User user = userMapper.toModel(userDto);
        if (userRepository.exists(getLoginPredicate(userDto.getLogin()))) {
            log.info("user with username {} exists!",user.getLogin());
            throw new UserException("user with username " + user.getLogin() + " exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("user {} saved",user.getLogin());
        return user;
    }

    public void updateUser(UserDto userDto) throws UserException{
        if (userRepository.exists(getLoginPredicate(userDto.getLogin()))) {
            User user = userMapper.toModel(userDto);
            userRepository.save(user);
            log.info("USER {} UPDATED", user.getLogin());
            return;
        }
        throw new UserException("user doesn't exist");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUsers(Long id) {
        userRepository.deleteById(id);
        log.info("USER WITH ID {} DELETED", id);
    }

    public User getById(Long id) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserException("user with id " + id + " not found");
        }
        return optionalUser.get();
    }

    public UserDto getUserAuthInfo(HttpServletRequest request) throws UserException {
        String authHeader = request.getHeader(AUTHORIZATION);
        String login = getLogin(authHeader);
        Optional<User> userOpt = userRepository.findOne(getLoginPredicate(login));
        if (userOpt.isEmpty()) {
            throw new UserException("user not found");
        }
        log.info("USER WITH ID {} UPDATED",userOpt.get().getId());
        return userMapper.toDto(userOpt.get());
    }

    public void alterUserAuthInfo(HttpServletRequest request, UserDto userDto) throws UserException{
        String authHeader = request.getHeader(AUTHORIZATION);
        String login = getLogin(authHeader);
        Optional<User> userOpt = userRepository.findOne(getLoginPredicate(login));
        if (userOpt.isEmpty()) {
            throw new UserException("user not found");
        }
        User user = userOpt.get();
        Optional.ofNullable(userDto.getName()).ifPresent(user::setName);
        Optional.ofNullable(userDto.getSurname()).ifPresent(user::setSurname);
        Optional.ofNullable(userDto.getPatronymic()).ifPresent(user::setPatronymic);
        Optional.ofNullable(userDto.getBornDate()).ifPresent(user::setBornDate);
        Optional.ofNullable(userDto.getTown()).ifPresent(user::setTown);
        Optional.ofNullable(userDto.getStreet()).ifPresent(user::setStreet);
        Optional.ofNullable(userDto.getFlat()).ifPresent(user::setFlat);
        Optional.ofNullable(userDto.getPassportNum()).ifPresent(user::setPassportNum);
        Optional.ofNullable(userDto.getIssuedBy()).ifPresent(user::setIssuedBy);
        userRepository.save(user);
        log.info("USER WITH ID {} DELETED",user.getId());
    }

    public void changePassword(HttpServletRequest request, PasswordChanger changer) throws UserException{
        String authHeader = request.getHeader(AUTHORIZATION);
        String login = getLogin(authHeader);
        Optional<User> userOpt = userRepository.findOne(getLoginPredicate(login));
        if (userOpt.isEmpty()) {
            throw new UserException("user not found");
        }
        User user = userOpt.get();
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

    private Predicate getLoginPredicate(String login) {
        return QPredicate.builder()
                .add(login, QUser.user.login::eq)
                .buildAnd();
    }

    public Predicate getSearchPredicate(Map<String, String> params) {

        Predicate predicate = QPredicate.builder()
                .add(params.get("name"), QUser.user.name::eq)
                .add(params.get("surname"), QUser.user.surname::eq)
                .add(params.get("patronymic"), QUser.user.patronymic::eq)
                .add(params.get("beforeBordDate"), QUser.user.patronymic::eq)
                .add(params.get("afterBornDate"), QUser.user.patronymic::eq)
                .add(params.get("town"), QUser.user.town::eq)
                .add(params.get("street"), QUser.user.street::eq)
                .add(params.get("house"), QUser.user.street::eq)
                .add(params.get("flat"), QUser.user.street::eq)
                .buildAnd();
        return null;
    }

    public static void main(String[] args) throws ParseException {
        String j = "2003-07-04";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date h = dateFormat.parse(j);
//        Date h = new Date(j);
        System.out.println(h);
    }
}
