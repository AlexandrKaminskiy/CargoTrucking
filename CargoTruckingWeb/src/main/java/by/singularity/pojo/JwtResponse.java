package by.singularity.pojo;

import lombok.AllArgsConstructor;
import java.util.Set;

@AllArgsConstructor
public class JwtResponse {
    private String token;
    private final String TYPE = "BEARER";
    private Long id;
    private String login;
    private String email;
    private Set<String> userRoles;

}
