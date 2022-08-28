package by.singularity.service;

import by.singularity.entity.User;
import by.singularity.repository.jparepo.UserJpaRepository;
import by.singularity.repository.impl.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository clientRepository;
    private final UserJpaRepository clientJpaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> findClient(String login){
        return clientRepository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> clientOpt = clientRepository.findByLogin(username);
        if (clientOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User client = clientOpt.get();
        log.info("USER {} WAS FOUND", client.getName());
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        client.getRoles().forEach((c)->grantedAuthorities.add(new SimpleGrantedAuthority(c.toString())));
        return new org.springframework.security.core.userdetails.User(client.getLogin(), client.getPassword(), grantedAuthorities);

    }

    public void saveUser(User client) {
        if (clientRepository.findByLogin(client.getLogin()).isPresent()) {
            log.info("user with username {} exists!",client.getLogin());
            return;
        }
        //todo убрать заглушку
        log.info("user {} saved",client.getLogin());
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientJpaRepository.save(client);
    }
}
