package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne(cascade = CascadeType.ALL)
    private Invoice invoice;

    private Integer distance;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "car_id")
    private Car car;

    private Date endDate;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "verifier_id")
    private User verifier;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wayBill")
    private Set<Checkpoint> checkpoints;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CarriageStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "carriage_status", joinColumns = @JoinColumn(name = "waybill_id"))
    private Set<CarriageStatus> carriageStatuses;

    private Integer consumption;

    private Integer income;

    private Integer profit;

    public void setCheckpoints(Set<Checkpoint> checkpoints) {
        if (checkpoints != null) {
            checkpoints.forEach(checkpoint -> checkpoint.setWayBill(this));
        }
        this.checkpoints = checkpoints;
    }
}