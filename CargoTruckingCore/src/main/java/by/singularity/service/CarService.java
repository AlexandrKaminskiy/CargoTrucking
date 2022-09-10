package by.singularity.service;

import by.singularity.dto.CarDto;
import by.singularity.entity.Car;
import by.singularity.exception.CarException;
import by.singularity.mapper.CarMapper;
import by.singularity.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public Car createCar(CarDto carDto) {
        Car car = carMapper.toModel(carDto);
        carRepository.save(car);
        log.info("CAR WITH ID {} CREATED", car.getId());
        return car;
    }

    public List<Car> getAllCars() {
        //todo
        return carRepository.findAll();
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
        log.info("CAR WITH ID {} DELETED", id);
    }

    public Car getCar(Long id) throws CarException {
        Optional<Car> carOpt = carRepository.findById(id);
        if (carOpt.isEmpty()) {
            throw new CarException("car with id " + id + "not found");
        }
        return carOpt.get();
    }

}

