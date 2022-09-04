package by.singularity.repository.customrepo;

import by.singularity.entity.Storage;
import com.querydsl.core.types.Predicate;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.function.Function;

public interface CustomStorageRepo extends CustomRepo<Storage, Long> {

}
