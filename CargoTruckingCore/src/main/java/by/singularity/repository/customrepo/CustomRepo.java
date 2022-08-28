package by.singularity.repository.customrepo;

import java.util.List;
import java.util.Optional;

public interface CustomRepo<T,ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
