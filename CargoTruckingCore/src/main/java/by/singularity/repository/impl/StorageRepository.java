package by.singularity.repository.impl;

import by.singularity.entity.Storage;
import by.singularity.repository.customrepo.CustomStorageRepo;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static by.singularity.entity.QStorage.storage;

@Repository
@RequiredArgsConstructor
public class StorageRepository implements CustomStorageRepo {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Storage> findById(Long id) {
        return Optional.ofNullable(new JPAQuery<Storage>(entityManager)
                .select(storage)
                .from(storage)
                .where(storage.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<Storage> findAll() {
        return new JPAQuery<Storage>(entityManager)
                .select(storage)
                .from(storage)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        jpaQueryFactory
                .delete(storage)
                .where(storage.id.eq(id))
                .execute();
    }
}
