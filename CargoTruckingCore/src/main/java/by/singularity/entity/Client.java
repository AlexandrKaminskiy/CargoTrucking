package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Length(max = 20)
    private String name;

    @Length(min = 1, max = 20)
    private String surname;

    @Length(max = 20)
    private String patronymic;

    @DateTimeFormat
    private Date date;

    @Email
    @Length(max = 50)
    private String email;

    @Length(max = 20)
    String town;

    @Length(max = 20)
    String street;
    @Max(10000)
    Integer house;

    @Max(10000)
    Integer flat;

    @Length(min = 5,max = 20)
    private String login;

    @Length(min = 5,max = 72)
    private String password;

    @Length(max = 30)
    private String passportNum;

    @Length(max = 50)
    private String issuedBy;


    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "client_id"))
    private Set<Role> roles;
}
