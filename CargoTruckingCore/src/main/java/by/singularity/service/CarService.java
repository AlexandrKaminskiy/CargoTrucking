package by.singularity.service;

import by.singularity.dto.CarDto;
import by.singularity.entity.Car;
import by.singularity.entity.QCar;
import by.singularity.exception.CarException;
import by.singularity.mapper.CarMapper;
import by.singularity.repository.CarRepository;
import by.singularity.repository.queryUtils.QPredicate;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
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

    public Page<Car> getAllCars(Pageable pageable, Map<String,String> params) {
        return carRepository.findAll(getFindingPredicate(params),pageable);
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
    private Predicate getFindingPredicate(Map<String,String> params) {
        return QPredicate.builder()
                .add(params.get("brand"), QCar.car.brand::eq)
                .add(params.get("carNumber"), QCar.car.carNumber::eq)
                .buildAnd();
    }
}

