package by.singularity.repository.jparepo;

import by.singularity.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageJpaRepository extends JpaRepository<Storage,Long> {
}
