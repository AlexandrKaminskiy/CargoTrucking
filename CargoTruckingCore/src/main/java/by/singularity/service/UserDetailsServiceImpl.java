package by.singularity.service;

import by.singularity.entity.Client;
import by.singularity.repository.impl.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client user = userRepository
                .findByLogin(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with this login not found"));
        UserDetailsImpl.mapToUserDetails(user);
        return null;
    }

}
