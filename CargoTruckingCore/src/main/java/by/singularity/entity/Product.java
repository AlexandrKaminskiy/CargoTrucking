package by.singularity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private User creator;

    @JsonIgnore
    @ManyToOne
    private ProductOwner productOwner;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = ProductStatus.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "product_status",joinColumns = @JoinColumn(name = "product_id"))
    private Set<ProductStatus> productStatus;
}