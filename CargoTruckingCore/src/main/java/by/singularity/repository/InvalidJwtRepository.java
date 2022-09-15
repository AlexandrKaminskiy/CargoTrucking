package by.singularity.repository;

import by.singularity.entity.InvalidJwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvalidJwtRepository extends JpaRepository<InvalidJwt, Long>, QuerydslPredicateExecutor<InvalidJwt> {
    Optional<InvalidJwt> findByJwt(String jwt);
}
