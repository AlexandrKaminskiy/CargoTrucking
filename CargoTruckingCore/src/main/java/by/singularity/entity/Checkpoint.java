package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "checkpoints")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = WayBill.class)
    @JoinColumn(name = "way_bill_id", referencedColumnName = "id")
    private WayBill wayBill;

    private String address;

    private Date requiredArrivalDate;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CheckpointStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "checkpoint_statuses",joinColumns = @JoinColumn(name = "checkpoint_id"))
    private Set<CheckpointStatus> status;
}