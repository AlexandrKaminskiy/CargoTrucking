package by.singularity.repository.customrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

public interface CustomRepo<T,ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
