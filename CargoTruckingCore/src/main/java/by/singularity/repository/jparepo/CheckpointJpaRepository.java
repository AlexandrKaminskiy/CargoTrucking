package by.singularity.repository.jparepo;

import by.singularity.entity.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointJpaRepository extends JpaRepository<Checkpoint,Long> {
}
