package by.singularity.repository.impl;

import by.singularity.entity.ProductOwner;
import by.singularity.repository.customrepo.CustomProductOwnerRepo;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static by.singularity.entity.QProductOwner.productOwner;

@Repository
@RequiredArgsConstructor
public class ProductOwnerRepository implements CustomProductOwnerRepo {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<ProductOwner> findById(Long id) {
        return Optional.ofNullable(new JPAQuery<ProductOwner>(entityManager)
                .select(productOwner)
                .from(productOwner)
                .where(productOwner.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<ProductOwner> findAll() {
        return new JPAQuery<ProductOwner>(entityManager)
                .select(productOwner)
                .from(productOwner)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        jpaQueryFactory
                .delete(productOwner)
                .where(productOwner.id.eq(id))
                .execute();
    }
}