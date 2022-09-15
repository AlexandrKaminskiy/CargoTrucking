package by.singularity.service.utils;

import by.singularity.entity.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.stream.Collectors;

public class JwtUtils {
    public static String createAccessToken(String url, User user, int expires, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expires))
                .withIssuer(url)
                .withClaim("roles", user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String createAccessToken(String url, by.singularity.entity.User user, int expires, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(user.getLogin())
                .withExpiresAt(new Date(System.currentTimeMillis() + expires))
                .withIssuer(url)
                .withClaim("roles", user.getRoles()
                        .stream()
                        .map(Role::toString)
                        .collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String createRefreshToken(String url, User user, int expires, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expires))
                .withIssuer(url)
                .sign(algorithm);
    }
}
