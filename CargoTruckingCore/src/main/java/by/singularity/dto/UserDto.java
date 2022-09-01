package by.singularity.dto;

import by.singularity.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    @Length(max = 20)
    private String name;
    @Length(min = 1, max = 20)
    private String surname;
    @Length(max = 20)
    private String patronymic;
    private Set<Long> clientId;
    @DateTimeFormat
    private Date bornDate;
    @Length(max = 50)
    @Email
    private String email;
    @Length(max = 20)
    private String town;
    @Length(max = 20)
    private String street;
    @Max(10000)
    private Integer house;
    @Max(10000)
    private Integer flat;
    @Length(min = 5, max = 20)
    private String login;
    @Length(min = 5, max = 72)
    private String password;
    @Length(max = 30)
    private String passportNum;
    @Length(max = 50)
    private String issuedBy;
    private Set<Role> roles;

}
