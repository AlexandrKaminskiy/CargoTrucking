package by.singularity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStorage is a Querydsl query type for Storage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStorage extends EntityPathBase<Storage> {

    private static final long serialVersionUID = -1088415522L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStorage storage = new QStorage("storage");

    public final StringPath address = createString("address");

    public final QUser client;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QStorage(String variable) {
        this(Storage.class, forVariable(variable), INITS);
    }

    public QStorage(Path<? extends Storage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStorage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStorage(PathMetadata metadata, PathInits inits) {
        this(Storage.class, metadata, inits);
    }

    public QStorage(Class<? extends Storage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.client = inits.isInitialized("client") ? new QUser(forProperty("client")) : null;
    }

}

