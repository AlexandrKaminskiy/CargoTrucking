package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Length(min = 1)
    private String brand;
    @Length(min = 1)
    private String carNumber;

    @OneToMany(mappedBy = "car")
    private Collection<WayBill> wayBill;

    public Collection<WayBill> getWayBill() {
        return wayBill;
    }

    public void setWayBill(Collection<WayBill> wayBill) {
        this.wayBill = wayBill;
    }
}