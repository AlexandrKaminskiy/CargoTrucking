package by.singularity.service;

import by.singularity.dto.UserDto;
import by.singularity.entity.User;
import by.singularity.mapper.impl.UserMapperImpl;
import by.singularity.pojo.PasswordChanger;
import by.singularity.repository.jparepo.UserJpaRepository;
import by.singularity.repository.impl.UserRepository;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.net.httpserver.Headers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapperImpl userMapper;

    @Value("${jwt.secret}")
    private String secret;

    public Optional<User> findClient(String login){
        return userRepository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByLogin(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = userOpt.get();
        log.info("USER {} WAS FOUND", user.getName());
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoles().forEach((c)->grantedAuthorities.add(new SimpleGrantedAuthority(c.toString())));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthorities);

    }

    public String registerUser(UserDto userDto) {
        User user = userMapper.toModel(userDto);
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            log.info("user with username {} exists!",user.getLogin());
            return "user exist";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userJpaRepository.save(user);
        log.info("user {} saved",user.getLogin());
        return "api/user/" + user.getId();
    }



    public void updateUser(UserDto userDto) {
        if (userRepository.findByLogin(userDto.getLogin()).isPresent()) {
            User user = userMapper.toModel(userDto);
            userJpaRepository.save(user);
            log.info("USER {} UPDATED", user.getLogin());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUsers(List<Long> ids) {
        ids.forEach(userRepository::deleteById);
    }

    public User getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;//todo
        }
        return optionalUser.get();
    }

    public UserDto getUserAuthInfo(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        String login = getLogin(authHeader);
        Optional<User> userOpt = userRepository.findByLogin(login);
        if (userOpt.isEmpty()) {
            return null;
        }
        return userMapper.toDto(userOpt.get());
    }

    public void alterUserAuthInfo(HttpServletRequest request, UserDto userDto) {
        String authHeader = request.getHeader(AUTHORIZATION);
        String login = getLogin(authHeader);
        Optional<User> userOpt = userRepository.findByLogin(login);
        if (userOpt.isEmpty()) {
            return;
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
        userJpaRepository.save(user);
    }

    public void changePassword(HttpServletRequest request, PasswordChanger changer) {
        String authHeader = request.getHeader(AUTHORIZATION);
        String login = getLogin(authHeader);
        Optional<User> userOpt = userRepository.findByLogin(login);
        if (userOpt.isEmpty()) {
            return;
        }
        System.out.println(passwordEncoder.encode("12345"));
        System.out.println(passwordEncoder.encode("12345"));
        User user = userOpt.get();
        if (passwordEncoder.matches(changer.getOldPassword(),user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changer.getNewPassword()));
            userJpaRepository.save(user);
        }
    }
    private String getLogin(String authHeader) {
        String token = authHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();
    }


}
