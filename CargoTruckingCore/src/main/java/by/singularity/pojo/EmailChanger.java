package by.singularity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailChanger {
    @Email
    @NotNull
    private String recipient;

    private String subject;

    @NotNull
    private String text;

    @NotNull
    private String password;
}
