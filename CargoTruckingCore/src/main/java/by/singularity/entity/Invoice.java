package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
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

    private Date creationDate;

    private Date verifiedDate;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "product_owner_id")
    private ProductOwner productOwner;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "driver_id")
    private User driver;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
    private Set<Product> products;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = InvoiceStatus.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "invoice_status",joinColumns = @JoinColumn(name = "invoice_number"))
    private Set<InvoiceStatus> status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "invoice")
    private WayBill wayBill;

    public void setProducts(Set<Product> products) {
        if (products != null) {
            products.forEach(p -> p.setInvoice(this));
        }
        this.products = products;
    }
}