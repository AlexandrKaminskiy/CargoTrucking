package by.singularity.controller;

import by.singularity.entity.Client;
import by.singularity.entity.Role;
import by.singularity.pojo.JwtResponse;
import by.singularity.pojo.LoginRequest;
import by.singularity.pojo.MessageResponse;
import by.singularity.pojo.SignupRequest;
import by.singularity.repository.ClientJpaRepository;
import by.singularity.repository.impl.ClientRepository;
import by.singularity.security.JwtUtils;
import by.singularity.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final ClientJpaRepository userJpaRespository;

    private final ClientRepository userRespository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {

        if (userRespository.findByLogin(signupRequest.getLogin()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }

        if (userRespository.findByEmail(signupRequest.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist"));
        }

        Client user = new Client();
        user.setEmail(signupRequest.getEmail());
        user.setLogin(signupRequest.getLogin());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRoles(Collections.singleton(Role.ADMIN));
        userJpaRespository.save(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }
}
