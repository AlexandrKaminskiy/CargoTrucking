package by.singularity.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private static String secret;
    @Value("${jwt.expiration}")
    private static String expiration;


    public static String createToken(String username, String url, List<String> authorities, TokenType tokenType) {
        Long expiration;
        if (tokenType.equals(TokenType.ACCESS)) {
            expiration = 0l;
        } else {
            expiration = 1l;
        }
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withIssuer(url)
                .withClaim("roles", authorities)
                .sign(algorithm);
    }


}
