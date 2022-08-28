package by.singularity.repository.customrepo;

import by.singularity.entity.Client;
import by.singularity.entity.User;

import java.util.List;
import java.util.Optional;

public interface CustomClientRepository extends CustomRepo<Client, Long>{
    List<Client> findByName(String name);
}
