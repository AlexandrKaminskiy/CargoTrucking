package by.singularity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
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

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "creator_id")
    private User creator;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "product_owner_id")
    private ProductOwner productOwner;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = ProductStatus.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "product_status",joinColumns = @JoinColumn(name = "product_id"))
    private Set<ProductStatus> productStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name) && productStatus.equals(product.productStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, productStatus);
    }

}