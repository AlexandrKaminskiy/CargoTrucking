package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = ClientStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "status", joinColumns = @JoinColumn(name = "client_id"))
    private Set<ClientStatus> status;

    @ManyToOne
    private User adminInfo;
}