package by.singularity.entity;

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
@Table(name = "way_bill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WayBill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(targetEntity = Invoice.class)
    private Invoice invoice;

    private Integer distance;

    @ManyToOne(targetEntity = Car.class)
    private Car car;

    private Date endDate;

    @ManyToOne(targetEntity = User.class)
    private User verifier;

    @OneToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "way_bill_id")
    private Set<Checkpoint> checkpoints;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CarriageStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "carriage_status", joinColumns = @JoinColumn(name = "waybill_id"))
    private Set<CarriageStatus> carriageStatuses;
}