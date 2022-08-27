package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product_owner")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Length(min = 1, max = 40)
    private String name;

    @OneToMany
    private Set<Product> products;

}