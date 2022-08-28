package by.singularity.repository.impl;

import by.singularity.entity.Car;
import by.singularity.entity.Product;
import by.singularity.repository.customrepo.CustomProductRepo;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static by.singularity.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepository implements CustomProductRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(new JPAQuery<Product>(entityManager)
                .select(product)
                .from(product)
                .where(product.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<Product> findAll() {
        return new JPAQuery<Product>(entityManager)
                .select(product)
                .from(product)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        jpaQueryFactory
                .delete(product)
                .where(product.id.eq(id))
                .execute();
    }
}
