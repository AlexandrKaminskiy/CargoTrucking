package by.singularity.security;

import by.singularity.entity.InvalidJwt;
import by.singularity.entity.User;
import by.singularity.repository.InvalidJwtRepository;
import by.singularity.service.UserService;
import by.singularity.service.utils.JwtUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;
    private final InvalidJwtRepository invalidJwtRepository;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration.refresh}")
    private Integer expires;

    public Map<String,String> refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        Map<String, String> tokens = new HashMap<>();
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String refresh_token = authHeader.substring("Bearer ".length());
                if (invalidJwtRepository.findByJwt(refresh_token).isPresent()) {
                    throw new Exception();
                }
                Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.findUser(username).orElseThrow();
                String access_token = JwtUtils.createAccessToken(request.getRequestURL().toString(), user, expires, secret);
                InvalidJwt invalidJwt = new InvalidJwt();
                invalidJwt.setJwt(refresh_token);
                invalidJwtRepository.save(invalidJwt);
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                return tokens;
            }
        } catch (Exception ignored) {
        }
        tokens.put("error","check your token");
        return tokens;
    }

    public void logout(HttpServletRequest httpServletRequest) {
        String accessToken = httpServletRequest.getHeader(AUTHORIZATION).substring("Bearer ".length());
        InvalidJwt invalidJwt = new InvalidJwt();
        invalidJwt.setJwt(accessToken);
        invalidJwtRepository.save(invalidJwt);
    }
}
