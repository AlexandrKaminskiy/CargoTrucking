package by.singularity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWayBill is a Querydsl query type for WayBill
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWayBill extends EntityPathBase<WayBill> {

    private static final long serialVersionUID = 1925458425L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWayBill wayBill = new QWayBill("wayBill");

    public final QCar car;

    public final SetPath<CarriageStatus, EnumPath<CarriageStatus>> carriageStatuses = this.<CarriageStatus, EnumPath<CarriageStatus>>createSet("carriageStatuses", CarriageStatus.class, EnumPath.class, PathInits.DIRECT2);

    public final SetPath<Checkpoint, QCheckpoint> checkpoints = this.<Checkpoint, QCheckpoint>createSet("checkpoints", Checkpoint.class, QCheckpoint.class, PathInits.DIRECT2);

    public final NumberPath<Integer> distance = createNumber("distance", Integer.class);

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInvoice invoice;

    public final QUser verifier;

    public QWayBill(String variable) {
        this(WayBill.class, forVariable(variable), INITS);
    }

    public QWayBill(Path<? extends WayBill> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWayBill(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWayBill(PathMetadata metadata, PathInits inits) {
        this(WayBill.class, metadata, inits);
    }

    public QWayBill(Class<? extends WayBill> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.car = inits.isInitialized("car") ? new QCar(forProperty("car")) : null;
        this.invoice = inits.isInitialized("invoice") ? new QInvoice(forProperty("invoice"), inits.get("invoice")) : null;
        this.verifier = inits.isInitialized("verifier") ? new QUser(forProperty("verifier")) : null;
    }

}

