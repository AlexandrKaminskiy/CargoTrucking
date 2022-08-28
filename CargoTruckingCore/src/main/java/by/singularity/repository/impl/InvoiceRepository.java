package by.singularity.repository.impl;

import by.singularity.entity.Client;
import by.singularity.entity.Invoice;
import by.singularity.entity.User;
import by.singularity.repository.customrepo.CustomClientRepository;
import by.singularity.repository.customrepo.CustomInvoiceRepo;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static by.singularity.entity.QInvoice.invoice;

@Repository
@RequiredArgsConstructor
public class InvoiceRepository implements CustomInvoiceRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Invoice> findById(String number) {
        return Optional.ofNullable(new JPAQuery<Invoice>(entityManager)
                .select(invoice)
                .from(invoice)
                .where(invoice.number.eq(number))
                .fetchOne());
    }

    @Override
    public List<Invoice> findAll() {
        return new JPAQuery<Invoice>(entityManager)
                .select(invoice)
                .from(invoice)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteById(String number) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        jpaQueryFactory
                .delete(invoice)
                .where(invoice.number.eq(number))
                .execute();
    }
}

