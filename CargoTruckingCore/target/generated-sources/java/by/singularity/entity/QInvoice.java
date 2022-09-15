package by.singularity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInvoice is a Querydsl query type for Invoice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvoice extends EntityPathBase<Invoice> {

    private static final long serialVersionUID = -1538909808L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvoice invoice = new QInvoice("invoice");

    public final DateTimePath<java.util.Date> creationDate = createDateTime("creationDate", java.util.Date.class);

    public final QUser creator;

    public final QUser driver;

    public final StringPath number = createString("number");

    public final QProductOwner productOwner;

    public final SetPath<Product, QProduct> products = this.<Product, QProduct>createSet("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public final SetPath<InvoiceStatus, EnumPath<InvoiceStatus>> status = this.<InvoiceStatus, EnumPath<InvoiceStatus>>createSet("status", InvoiceStatus.class, EnumPath.class, PathInits.DIRECT2);

    public final QStorage storage;

    public final DateTimePath<java.util.Date> verifiedDate = createDateTime("verifiedDate", java.util.Date.class);

    public QInvoice(String variable) {
        this(Invoice.class, forVariable(variable), INITS);
    }

    public QInvoice(Path<? extends Invoice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInvoice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInvoice(PathMetadata metadata, PathInits inits) {
        this(Invoice.class, metadata, inits);
    }

    public QInvoice(Class<? extends Invoice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new QUser(forProperty("creator"), inits.get("creator")) : null;
        this.driver = inits.isInitialized("driver") ? new QUser(forProperty("driver"), inits.get("driver")) : null;
        this.productOwner = inits.isInitialized("productOwner") ? new QProductOwner(forProperty("productOwner")) : null;
        this.storage = inits.isInitialized("storage") ? new QStorage(forProperty("storage"), inits.get("storage")) : null;
    }

}

