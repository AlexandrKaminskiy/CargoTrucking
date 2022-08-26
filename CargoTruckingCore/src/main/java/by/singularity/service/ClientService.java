package by.singularity.service;

import by.singularity.entity.Client;
import by.singularity.repository.ClientJpaRepository;
import by.singularity.repository.impl.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final ClientJpaRepository clientJpaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<Client> findClient(String login){
        return clientRepository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> clientOpt = clientRepository.findByLogin(username);
        if (clientOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Client client = clientOpt.get();
        log.info("USER {} WAS FOUND", client.getName());
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        client.getRoles().forEach((c)->grantedAuthorities.add(new SimpleGrantedAuthority(c.toString())));
        return new User(client.getLogin(), client.getPassword(), grantedAuthorities);

    }

    public void saveUser(Client client) {
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
