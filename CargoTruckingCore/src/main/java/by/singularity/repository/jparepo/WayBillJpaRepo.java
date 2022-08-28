package by.singularity.repository.jparepo;

import by.singularity.entity.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WayBillJpaRepo extends JpaRepository<WayBill,Long> {
}
