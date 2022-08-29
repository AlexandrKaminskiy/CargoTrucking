package by.singularity.mapper.impl;

import by.singularity.dto.CarDto;
import by.singularity.entity.Car;
import by.singularity.mapper.CarMapper;
import org.springframework.stereotype.Component;

@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public Car toModel(CarDto carDto) {
        if ( carDto == null ) {
            return null;
        }
        Car car = new Car();
        car.setId( carDto.getId() );
        car.setBrand( carDto.getBrand() );
        car.setCarNumber( carDto.getCarNumber() );
        return car;
    }

    @Override
    public CarDto toDto(Car car) {
        if ( car == null ) {
            return null;
        }
        Long id = car.getId();
        String brand = car.getBrand();
        String carNumber = car.getCarNumber();
        return new CarDto( id, brand, carNumber );
    }
}
