package by.singularity.mapper.impl;

import by.singularity.dto.CarDto;
import by.singularity.entity.Car;
import by.singularity.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CarMapper implements Mapper<Car, CarDto> {
    private final ModelMapper mapper;

    @Override
    public Car toModel(CarDto carDto) {
        return Objects.isNull(carDto) ? null : mapper.map(carDto, Car.class);
    }

    @Override
    public CarDto toDto(Car car) {
        return Objects.isNull(car) ? null : mapper.map(car, CarDto.class);
    }
}
