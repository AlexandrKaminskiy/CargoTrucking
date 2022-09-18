package by.singularity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChanger {
    private String oldPassword;
    @Length(min = 5, max = 72)
    private String newPassword;
}
