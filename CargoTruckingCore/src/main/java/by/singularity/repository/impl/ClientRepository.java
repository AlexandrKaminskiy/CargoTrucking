package by.singularity.repository.impl;

import by.singularity.entity.Client;
import by.singularity.entity.User;
import by.singularity.repository.customrepo.CustomClientRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static by.singularity.entity.QClient.client;

@Repository
@RequiredArgsConstructor
public class ClientRepository implements CustomClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Client> findByName(String name) {
        return new JPAQuery<Client>(entityManager)
                .select(client)
                .from(client)
                .where(client.name.eq(name))
                .fetch();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(new JPAQuery<Client>(entityManager)
                .select(client)
                .from(client)
                .where(client.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<Client> findAll() {
        return new JPAQuery<Client>(entityManager)
                .select(client)
                .from(client)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        jpaQueryFactory
                .delete(client)
                .where(client.id.eq(id))
                .execute();
    }
}
