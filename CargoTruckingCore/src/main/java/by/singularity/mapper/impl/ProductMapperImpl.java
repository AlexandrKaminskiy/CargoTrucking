package by.singularity.mapper.impl;

import by.singularity.dto.ProductDto;
import by.singularity.entity.Product;
import by.singularity.entity.ProductStatus;
import by.singularity.mapper.ProductMapper;

import javax.annotation.Generated;
import java.util.LinkedHashSet;
import java.util.Set;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-30T01:49:14+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toModel(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productDto.getId() );
        product.setName( productDto.getName() );
        product.setAmount( productDto.getAmount() );
        Set<ProductStatus> set = productDto.getProductStatus();
        if ( set != null ) {
            product.setProductStatus( new LinkedHashSet<ProductStatus>( set ) );
        }

        return product;
    }

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Set<ProductStatus> productStatus = null;
        Long id = null;
        String name = null;
        Integer amount = null;

        Set<ProductStatus> set = product.getProductStatus();
        if ( set != null ) {
            productStatus = new LinkedHashSet<ProductStatus>( set );
        }
        id = product.getId();
        name = product.getName();
        amount = product.getAmount();

        Long creatorId = null;

        ProductDto productDto = new ProductDto( id, name, amount, creatorId, productStatus );

        return productDto;
    }
}
