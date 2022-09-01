package by.singularity.mapper;

import by.singularity.dto.ProductDto;
import by.singularity.dto.ProductOwnerDto;
import by.singularity.entity.Product;
import by.singularity.entity.ProductOwner;
import org.mapstruct.Mapper;

@Mapper
public interface ProductOwnerMapper {
    ProductOwner toModel(ProductOwnerDto productOwnerDto);
    ProductOwnerDto toDto(ProductOwner productOwner);
}
