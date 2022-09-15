package by.singularity.security.filter;

import by.singularity.repository.InvalidJwtRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
@Setter
public class AppAuthorizationFilter extends OncePerRequestFilter {
    private final InvalidJwtRepository invalidJwtRepository;
    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/register")) {
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getServletPath().equals("/api/refresh")) {
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getServletPath().equals("/api/sign-in")) {
            filterChain.doFilter(request,response);
            return;
        }
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring("Bearer ".length());
                if (invalidJwtRepository.findByJwt(token).isPresent()) {
                    throw new Exception();
                }
                Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                Arrays.stream(roles).forEach((role)-> authorities.add(new SimpleGrantedAuthority(role)));
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username,null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request,response);
            } catch (Exception e){
                e.printStackTrace();
                log.error("INVALID ACCESS TOKEN");
                Map<String, String> resp = new HashMap<>();
                resp.put("error","check your access token");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), resp);
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
            return;
        }
        log.info("SUCCESSFULLY AUTHORIZED!");
        filterChain.doFilter(request,response);
    }

}
