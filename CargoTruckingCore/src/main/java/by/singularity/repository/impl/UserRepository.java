package by.singularity.repository.impl;

import static by.singularity.entity.QUser.user;
import by.singularity.entity.User;
import by.singularity.repository.CustomUserRepository;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements CustomUserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findByName(String name) {
        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(user.name.equalsIgnoreCase(name))
                .fetch();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(user.email.equalsIgnoreCase(email))
                .fetchOne());
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(user.login.eq(login))
                .fetchOne());
    }
}
