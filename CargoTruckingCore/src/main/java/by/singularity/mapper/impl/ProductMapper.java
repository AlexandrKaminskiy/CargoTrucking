package by.singularity.mapper.impl;

import by.singularity.dto.ProductDto;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.Product;
import by.singularity.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductMapper implements Mapper<Product, ProductDto> {
    private final ModelMapper mapper;

    @Override
    public Product toModel(ProductDto productDto) {
        return Objects.isNull(productDto) ? null : mapper.map(productDto, Product.class);
    }

    @Override
    public ProductDto toDto(Product product) {
        return Objects.isNull(product) ? null : mapper.map(product, ProductDto.class);
    }
}
