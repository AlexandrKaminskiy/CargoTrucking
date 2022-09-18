package by.singularity.repository;

import by.singularity.entity.RepairingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairingMailRepository extends JpaRepository<RepairingMessage, Long> {
    Optional<RepairingMessage> findByUuid(String uuid);
}
