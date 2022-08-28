package by.singularity.repository.customrepo;

import by.singularity.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CustomCarRepo extends CustomRepo<Car, Long>{
    List<Car> findByCarNumber(String carNumber);
}
