package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

@Entity
@Table(name = "invoices")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Invoice {
    @Id
    @Column(name = "id", nullable = false)
    private String number;

    @ManyToOne(targetEntity = Storage.class)
    private Storage storage;

    @ManyToOne(targetEntity = User.class)
    private User creator;

    @ManyToOne(targetEntity = ProductOwner.class)
    private ProductOwner productOwner;

    @ManyToOne(targetEntity = User.class)
    private User driver;

    @OneToMany(targetEntity = Product.class)
    private Set<Product> products;

}