package by.singularity.repository;

import by.singularity.entity.ProductOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOwnerRepository extends JpaRepository<ProductOwner,Long>, QuerydslPredicateExecutor<ProductOwner> {
}
