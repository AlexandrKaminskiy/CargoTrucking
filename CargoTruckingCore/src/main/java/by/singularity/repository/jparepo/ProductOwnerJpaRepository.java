package by.singularity.repository.jparepo;

import by.singularity.entity.ProductOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOwnerJpaRepository extends JpaRepository<ProductOwner,Long> {
}
