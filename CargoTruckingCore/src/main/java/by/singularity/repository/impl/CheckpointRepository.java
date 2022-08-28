package by.singularity.repository.impl;

import by.singularity.entity.Checkpoint;
import by.singularity.repository.customrepo.CustomCheckpointRepo;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static by.singularity.entity.QCheckpoint.checkpoint;

@Repository
@RequiredArgsConstructor
public class CheckpointRepository implements CustomCheckpointRepo {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Checkpoint> findById(Long id) {
        return Optional.ofNullable(new JPAQuery<Checkpoint>(entityManager)
                .select(checkpoint)
                .from(checkpoint)
                .where(checkpoint.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<Checkpoint> findAll() {
        return new JPAQuery<Checkpoint>(entityManager)
                .select(checkpoint)
                .from(checkpoint)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        jpaQueryFactory
                .delete(checkpoint)
                .where(checkpoint.id.eq(id))
                .execute();
    }
}
