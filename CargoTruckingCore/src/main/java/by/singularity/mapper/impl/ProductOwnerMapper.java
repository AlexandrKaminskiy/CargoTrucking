package by.singularity.mapper.impl;

import by.singularity.dto.ProductOwnerDto;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.ProductOwner;
import by.singularity.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductOwnerMapper implements Mapper<ProductOwner, ProductOwnerDto> {
    private final ModelMapper mapper;

    @Override
    public ProductOwner toModel(ProductOwnerDto productOwnerDto) {
        return Objects.isNull(productOwnerDto) ? null : mapper.map(productOwnerDto, ProductOwner.class);
    }

    @Override
    public ProductOwnerDto toDto(ProductOwner productOwner) {
        return Objects.isNull(productOwner) ? null : mapper.map(productOwner, ProductOwnerDto.class);
    }
}
