package by.singularity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
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

    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productOwner")
    private Set<Product> products;

    @OneToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.PERSIST},
            mappedBy = "productOwner")
    private Collection<Invoice> invoice;

    public void setProducts(Set<Product> products) {
        if (products != null) {
            products.forEach(product -> product.setProductOwner(this));
        }
        this.products = products;
    }
}