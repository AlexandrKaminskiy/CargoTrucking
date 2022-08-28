package by.singularity.repository.jparepo;

import by.singularity.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceJpaRepository extends JpaRepository<Invoice,String> {
}
