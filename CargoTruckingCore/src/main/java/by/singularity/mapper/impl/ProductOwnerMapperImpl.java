package by.singularity.mapper.impl;

import by.singularity.dto.ProductDto;
import by.singularity.dto.ProductOwnerDto;
import by.singularity.entity.Product;
import by.singularity.entity.ProductOwner;
import by.singularity.entity.ProductStatus;
import by.singularity.mapper.ProductOwnerMapper;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class ProductOwnerMapperImpl implements ProductOwnerMapper {

    @Override
    public ProductOwner toModel(ProductOwnerDto productOwnerDto) {
        if ( productOwnerDto == null ) {
            return null;
        }

        ProductOwner productOwner = new ProductOwner();

        productOwner.setName( productOwnerDto.getName() );
        productOwner.setProducts( productDtoSetToProductSet( productOwnerDto.getProducts() ) );

        return productOwner;
    }

    @Override
    public ProductOwnerDto toDto(ProductOwner productOwner) {
        if ( productOwner == null ) {
            return null;
        }

        Set<ProductDto> products;
        String name;

        products = productSetToProductDtoSet( productOwner.getProducts() );
        name = productOwner.getName();

        return new ProductOwnerDto( name, products );
    }

    protected Product productDtoToProduct(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productDto.getId() );
        product.setName( productDto.getName() );
        product.setAmount( productDto.getAmount() );
        Set<ProductStatus> set = productDto.getProductStatus();
        if ( set != null ) {
            product.setProductStatus( new LinkedHashSet<>( set ) );
        }

        return product;
    }

    protected Set<Product> productDtoSetToProductSet(Set<ProductDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Product> set1 = new LinkedHashSet<>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ProductDto productDto : set ) {
            set1.add( productDtoToProduct( productDto ) );
        }

        return set1;
    }

    protected ProductDto productToProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Set<ProductStatus> productStatus = null;
        Long id;
        String name;
        Integer amount;

        Set<ProductStatus> set = product.getProductStatus();
        if ( set != null ) {
            productStatus = new LinkedHashSet<>( set );
        }
        id = product.getId();
        name = product.getName();
        amount = product.getAmount();

        Long creatorId = null;

        return new ProductDto( id, name, amount, creatorId, productStatus );
    }

    protected Set<ProductDto> productSetToProductDtoSet(Set<Product> set) {
        if ( set == null ) {
            return null;
        }

        Set<ProductDto> set1 = new LinkedHashSet<ProductDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Product product : set ) {
            set1.add( productToProductDto( product ) );
        }

        return set1;
    }
}
