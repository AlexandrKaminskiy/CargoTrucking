package by.singularity.mapper;

import by.singularity.dto.ProductDto;
import by.singularity.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    Product toModel(ProductDto productDto);
    ProductDto toDto(Product product);
}
