package by.singularity.repository;

import by.singularity.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomClientRepository {
    List<Client> findByName(String name);
}
