package by.singularity.repository.customrepo;

import by.singularity.entity.User;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository extends CustomRepo<User, Long>{
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);

}
