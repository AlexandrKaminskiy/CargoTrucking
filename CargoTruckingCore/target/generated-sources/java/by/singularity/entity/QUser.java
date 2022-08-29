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

    public static final QUser user = new QUser("user");

    public final SetPath<Client, QClient> client = this.<Client, QClient>createSet("client", Client.class, QClient.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

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
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

