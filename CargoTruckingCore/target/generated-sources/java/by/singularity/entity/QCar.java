package by.singularity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCar is a Querydsl query type for Car
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCar extends EntityPathBase<Car> {

    private static final long serialVersionUID = 155844535L;

    public static final QCar car = new QCar("car");

    public final StringPath brand = createString("brand");

    public final StringPath carNumber = createString("carNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final CollectionPath<WayBill, QWayBill> wayBill = this.<WayBill, QWayBill>createCollection("wayBill", WayBill.class, QWayBill.class, PathInits.DIRECT2);

    public QCar(String variable) {
        super(Car.class, forVariable(variable));
    }

    public QCar(Path<? extends Car> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCar(PathMetadata metadata) {
        super(Car.class, metadata);
    }

}

