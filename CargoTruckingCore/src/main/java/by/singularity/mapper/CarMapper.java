package by.singularity.mapper;

import by.singularity.dto.CarDto;
import by.singularity.entity.Car;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {
    Car toModel(CarDto carDto);
    CarDto toDto(Car car);
}
