package by.singularity.dto;

import by.singularity.entity.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class UserDto implements Serializable {
    private final Long id;
    @Length(max = 20)
    private final String name;
    @Length(min = 1, max = 20)
    private final String surname;
    @Length(max = 20)
    private final String patronymic;
    @Min(1)
    private Long clientId;
    private final Date bornDate;
    @Length(max = 50)
    @Email
    private final String email;
    @Length(max = 20)
    private final String town;
    @Length(max = 20)
    private final String street;
    @Max(10000)
    private final Integer house;
    @Max(10000)
    private final Integer flat;
    @Length(min = 5, max = 20)
    private final String login;
    @Length(min = 5, max = 72)
    private final String password;
    @Length(max = 30)
    private final String passportNum;
    @Length(max = 50)
    private final String issuedBy;
    private final Set<Role> roles;
}
