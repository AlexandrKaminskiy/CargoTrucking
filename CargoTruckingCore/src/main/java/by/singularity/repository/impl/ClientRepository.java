package by.singularity.repository.impl;

import by.singularity.entity.Client;
import static by.singularity.entity.QClient.client;
import by.singularity.repository.CustomClientRepository;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
                .where(client.name.equalsIgnoreCase(name))
                .fetch();
    }


}
