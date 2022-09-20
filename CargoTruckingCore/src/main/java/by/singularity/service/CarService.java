package by.singularity.service;

import by.singularity.dto.CarDto;
import by.singularity.entity.Car;
import by.singularity.entity.QCar;
import by.singularity.entity.WayBill;
import by.singularity.exception.CarException;
import by.singularity.mapper.impl.CarMapper;
import by.singularity.repository.CarRepository;
import by.singularity.repository.WayBillRepository;
import by.singularity.repository.queryUtils.QPredicate;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final WayBillRepository wayBillRepository;

    public Car createCar(CarDto carDto) {
        Car car = carMapper.toModel(carDto);
        carRepository.save(car);
        log.info("CAR WITH ID {} CREATED", car.getId());
        return car;
    }

    public Page<Car> getAllCars(Pageable pageable, Map<String,String> params) {
        return carRepository.findAll(getFindingPredicate(params),pageable);
    }

    public void deleteCar(Long id) throws CarException {
        Car car = carRepository.findById(id).orElseThrow(()->new CarException("car with id " + id + " not found"));
        List<WayBill> wayBills = car.getWayBill();
        wayBills.forEach(wayBill->wayBill.setCar(null));
        wayBillRepository.saveAll(wayBills);
        carRepository.delete(car);
        log.info("CAR WITH ID {} DELETED", id);
    }

    public Car getCar(Long id) throws CarException {
        return carRepository.findById(id)
                .orElseThrow(()->new CarException("car with id " + id + "not found"));

    }
    private Predicate getFindingPredicate(Map<String,String> params) {
        return QPredicate.builder()
                .add(params.get("brand"), QCar.car.brand::eq)
                .add(params.get("carNumber"), QCar.car.carNumber::eq)
                .buildAnd();
    }
}

