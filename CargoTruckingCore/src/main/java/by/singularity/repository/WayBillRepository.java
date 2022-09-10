package by.singularity.repository;

import by.singularity.entity.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WayBillRepository extends JpaRepository<WayBill,Long>, QuerydslPredicateExecutor<WayBill> {
}
