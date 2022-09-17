package by.singularity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClient is a Querydsl query type for Client
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClient extends EntityPathBase<Client> {

    private static final long serialVersionUID = -85113592L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClient client = new QClient("client");

    public final DateTimePath<java.util.Date> activeDate = createDateTime("activeDate", java.util.Date.class);

    public final QUser adminInfo;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isActive = createBoolean("isActive");

    public final StringPath name = createString("name");

    public final SetPath<ClientStatus, EnumPath<ClientStatus>> status = this.<ClientStatus, EnumPath<ClientStatus>>createSet("status", ClientStatus.class, EnumPath.class, PathInits.DIRECT2);

    public final SetPath<Storage, QStorage> storages = this.<Storage, QStorage>createSet("storages", Storage.class, QStorage.class, PathInits.DIRECT2);

    public QClient(String variable) {
        this(Client.class, forVariable(variable), INITS);
    }

    public QClient(Path<? extends Client> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClient(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClient(PathMetadata metadata, PathInits inits) {
        this(Client.class, metadata, inits);
    }

    public QClient(Class<? extends Client> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.adminInfo = inits.isInitialized("adminInfo") ? new QUser(forProperty("adminInfo"), inits.get("adminInfo")) : null;
    }

}

