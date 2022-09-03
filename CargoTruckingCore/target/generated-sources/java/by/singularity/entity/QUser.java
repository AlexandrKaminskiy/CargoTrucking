package by.singularity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 536766536L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final DateTimePath<java.util.Date> bornDate = createDateTime("bornDate", java.util.Date.class);

    public final QClient client;

    public final StringPath email = createString("email");

    public final NumberPath<Integer> flat = createNumber("flat", Integer.class);

    public final NumberPath<Integer> house = createNumber("house", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath issuedBy = createString("issuedBy");

    public final StringPath login = createString("login");

    public final StringPath name = createString("name");

    public final StringPath passportNum = createString("passportNum");

    public final StringPath password = createString("password");

    public final StringPath patronymic = createString("patronymic");

    public final SetPath<Role, EnumPath<Role>> roles = this.<Role, EnumPath<Role>>createSet("roles", Role.class, EnumPath.class, PathInits.DIRECT2);

    public final StringPath street = createString("street");

    public final StringPath surname = createString("surname");

    public final StringPath town = createString("town");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.client = inits.isInitialized("client") ? new QClient(forProperty("client"), inits.get("client")) : null;
    }

}

