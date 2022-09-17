package by.singularity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 486384466L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final QUser creator;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInvoice invoice;

    public final StringPath name = createString("name");

    public final QProductOwner productOwner;

    public final SetPath<ProductStatus, EnumPath<ProductStatus>> productStatus = this.<ProductStatus, EnumPath<ProductStatus>>createSet("productStatus", ProductStatus.class, EnumPath.class, PathInits.DIRECT2);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new QUser(forProperty("creator"), inits.get("creator")) : null;
        this.invoice = inits.isInitialized("invoice") ? new QInvoice(forProperty("invoice"), inits.get("invoice")) : null;
        this.productOwner = inits.isInitialized("productOwner") ? new QProductOwner(forProperty("productOwner")) : null;
    }

}

