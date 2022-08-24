package by.singularity.repository;

import by.singularity.entity.Client;

import java.util.List;
import java.util.Optional;

public interface CustomClientRepository {
    List<Client> findByName(String name);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByLogin(String login);
}
