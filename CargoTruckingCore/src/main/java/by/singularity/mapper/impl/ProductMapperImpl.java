package by.singularity.mapper.impl;

import by.singularity.dto.ProductDto;
import by.singularity.entity.Product;
import by.singularity.entity.ProductStatus;
import by.singularity.mapper.ProductMapper;
import by.singularity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    private final UserRepository userRepository;
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
            product.setProductStatus(new LinkedHashSet<>(set) );
        }

        return product;
    }

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Set<ProductStatus> productStatus = null;
        Long id;
        String name;
        Integer amount;

        Set<ProductStatus> set = product.getProductStatus();
        if ( set != null ) {
            productStatus = new LinkedHashSet<>(set);
        }
        id = product.getId();
        name = product.getName();
        amount = product.getAmount();

        return new ProductDto( id, name, amount, productStatus );
    }
}
