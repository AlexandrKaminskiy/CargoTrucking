package by.singularity.repository.impl;

import by.singularity.entity.Invoice;
import by.singularity.entity.WayBill;
import by.singularity.repository.customrepo.CustomWayBillRepo;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static by.singularity.entity.QWayBill.wayBill;

@Repository
@RequiredArgsConstructor
public class WayBillRepo implements CustomWayBillRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<WayBill> findById(Long id) {
        return Optional.ofNullable(new JPAQuery<WayBill>(entityManager)
                .select(wayBill)
                .from(wayBill)
                .where(wayBill.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<WayBill> findAll() {
        return new JPAQuery<WayBill>(entityManager)
                .select(wayBill)
                .from(wayBill)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        jpaQueryFactory
                .delete(wayBill)
                .where(wayBill.id.eq(id))
                .execute();
    }
}
