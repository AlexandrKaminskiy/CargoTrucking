package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
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

    @Min(0)
    private Integer distance;

    @ManyToOne(targetEntity = Car.class)
    private Car car;

    @DateTimeFormat
    private Date endDate;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wayBill")
    private Set<Checkpoint> checkpoints = new java.util.LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CarriageStatus.class)
    @CollectionTable(name = "carriage_status", joinColumns = @JoinColumn(name = "waybill_id"))
    private Set<CarriageStatus> carriageStatuses;
}