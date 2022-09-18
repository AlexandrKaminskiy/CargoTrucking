package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "repairing_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairingMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String uuid;

    private Long expirationTime;

    private String email;

    private String oldEmail;
}