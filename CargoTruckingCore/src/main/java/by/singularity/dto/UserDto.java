package by.singularity.dto;

import by.singularity.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;

    @Length(max = 20)
    @NotNull
    @NotEmpty
    private String name;

    @Length(min = 1, max = 20)
    @NotNull
    @NotEmpty
    private String surname;

    @Length(max = 20)
    @NotNull
    @NotEmpty
    private String patronymic;

    private Long clientId;

    @DateTimeFormat
    @NotNull
    @NotEmpty
    private Date bornDate;

    @Length(max = 50)
    @Email
    @NotNull
    @NotEmpty
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
    @NotNull
    @NotEmpty
    private String login;

    @Length(min = 5, max = 72)
    @NotNull
    @NotEmpty
    private String password;

    @Length(max = 30)
    private String passportNum;

    @Length(max = 50)
    private String issuedBy;

    @NotNull
    @NotEmpty
    @Size(min = 1)
    private Set<Role> roles;

}
