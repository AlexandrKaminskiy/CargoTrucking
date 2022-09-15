package by.singularity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCheckpoint is a Querydsl query type for Checkpoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCheckpoint extends EntityPathBase<Checkpoint> {

    private static final long serialVersionUID = 699252805L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheckpoint checkpoint = new QCheckpoint("checkpoint");

    public final StringPath address = createString("address");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> requiredArrivalDate = createDateTime("requiredArrivalDate", java.util.Date.class);

    public final QWayBill wayBill;

    public QCheckpoint(String variable) {
        this(Checkpoint.class, forVariable(variable), INITS);
    }

    public QCheckpoint(Path<? extends Checkpoint> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCheckpoint(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCheckpoint(PathMetadata metadata, PathInits inits) {
        this(Checkpoint.class, metadata, inits);
    }

    public QCheckpoint(Class<? extends Checkpoint> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.wayBill = inits.isInitialized("wayBill") ? new QWayBill(forProperty("wayBill"), inits.get("wayBill")) : null;
    }

}

