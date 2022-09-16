package by.singularity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOwner is a Querydsl query type for ProductOwner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOwner extends EntityPathBase<ProductOwner> {

    private static final long serialVersionUID = 797876865L;

    public static final QProductOwner productOwner = new QProductOwner("productOwner");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<Product, QProduct> products = this.<Product, QProduct>createList("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public QProductOwner(String variable) {
        super(ProductOwner.class, forVariable(variable));
    }

    public QProductOwner(Path<? extends ProductOwner> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductOwner(PathMetadata metadata) {
        super(ProductOwner.class, metadata);
    }

}

