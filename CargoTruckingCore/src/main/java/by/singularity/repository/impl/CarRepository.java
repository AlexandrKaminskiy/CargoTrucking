package by.singularity.repository.impl;

import by.singularity.entity.Car;
import by.singularity.repository.customrepo.CustomCarRepo;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import static by.singularity.entity.QCar.car;

@Repository
@RequiredArgsConstructor
public class CarRepository implements CustomCarRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Car> findByCarNumber(String carNumber) {
        new JPAQuery<Car>(entityManager)
                .select(car)
                .from(car)
                .where(car.carNumber.eq(carNumber))
                .fetch();
        return new JPAQuery<Car>(entityManager)
                .select(car)
                .from(car)
                .where(car.carNumber.eq(carNumber))
                .fetch();
//        new Page

    }

    @Override
    public Optional<Car> findById(Long id) {
        return Optional.ofNullable(new JPAQuery<Car>(entityManager)
                .select(car)
                .from(car)
                .where(car.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<Car> findAll() {
        return new JPAQuery<Car>(entityManager)
                .select(car)
                .from(car)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        jpaQueryFactory
                .delete(car)
                .where(car.id.eq(id))
                .execute();
    }
}
