package by.singularity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailParams {
    @NotNull
    @Email
    String to;

    @NotNull
    String subject;

    @NotNull
    String text;
}
