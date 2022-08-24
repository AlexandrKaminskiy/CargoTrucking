package by.singularity.repository;

import by.singularity.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRole,Long> {
}
