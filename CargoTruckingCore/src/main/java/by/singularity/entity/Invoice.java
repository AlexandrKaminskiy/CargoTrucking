package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(targetEntity = Storage.class)
    private Storage storage;

    @ManyToOne(targetEntity = User.class)
    private User creator;

    @ManyToOne(targetEntity = ProductOwner.class)
    private ProductOwner productOwner;

    @ManyToOne(targetEntity = User.class)
    private User driver;

    @OneToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "invoice_id")
    private Set<Product> products;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = InvoiceStatus.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "invoice_status",joinColumns = @JoinColumn(name = "invoice_number"))
    private Set<InvoiceStatus> status;

}