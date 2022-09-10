package by.singularity.service;

import by.singularity.dto.CarDto;
import by.singularity.entity.Car;
import by.singularity.mapper.CarMapper;
import by.singularity.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public Car createCar(CarDto carDto) {
        Car car = carMapper.toModel(carDto);
        carRepository.save(car);
        return car;
    }

    public List<Car> getAllCars() {
        //todo
        return carRepository.findAll();
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public Car getCar(Long id) {
        Optional<Car> carOpt = carRepository.findById(id);
        if (carOpt.isEmpty()) {
            //todo
            return null;
        }
        return carOpt.get();
    }

}

