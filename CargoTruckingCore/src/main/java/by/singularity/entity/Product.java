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
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private Integer amount;

    @ManyToOne
    private User creator;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = ProductStatus.class)
    @CollectionTable(name = "product_status",joinColumns = @JoinColumn(name = "product_id"))
    private Set<ProductStatus> productStatus;
}