package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name = "way_bill_id")
    private WayBill wayBill;

    private String address;

    private Date requiredArrivalDate;

    private Date checkpointDate;

}