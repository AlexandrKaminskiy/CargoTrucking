package by.singularity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private Boolean isActive;

    private Date activeDate;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = ClientStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "status", joinColumns = @JoinColumn(name = "client_id"))
    private Set<ClientStatus> status;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "client_id")
    private Set<Storage> storages;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private User adminInfo;


}