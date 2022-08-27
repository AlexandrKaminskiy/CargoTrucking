package by.singularity.controller;

import by.singularity.entity.User;
import by.singularity.entity.Role;
import by.singularity.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InfoController {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expires;

    private final UserService clientService;

    @GetMapping("/about")
    public String hello(){
        return "about";
    }


    @PostMapping("/register")
    public User register(@RequestBody @Valid User client) {
        clientService.saveUser(client);
        return clientService.findClient(client.getLogin()).get();
    }

    @GetMapping("/admininfo")
    public String admininfo() {
        return "My name is Gustavo, but u can call me SUS!";
    }

    @GetMapping("/managerinfo")
    public String managerinfo() {
        return "My name is Tomas Shelby";
    }

    @GetMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User client = clientService.findClient(username).get();
                String access_token = JWT.create()
                        .withSubject(client.getLogin())
                        .withExpiresAt(new Date(System.currentTimeMillis() + expires))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", client.getRoles()
                                .stream()
                                .map(Role::toString)
                                .collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e){
                e.printStackTrace();
                response.setHeader("error","check your token");
            }
        }
    }
}


