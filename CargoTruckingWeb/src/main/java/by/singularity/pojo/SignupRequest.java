package by.singularity.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String login;
    private String email;
    private String password;
}
