package by.singularity.repository;

import by.singularity.entity.User;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository {
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);
}
