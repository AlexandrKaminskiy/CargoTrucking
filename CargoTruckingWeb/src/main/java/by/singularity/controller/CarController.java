package by.singularity.controller;

import by.singularity.dto.CarDto;
import by.singularity.entity.Car;
import by.singularity.exception.CarException;
import by.singularity.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping()
    public void getAllCars(HttpServletResponse response,
                           @RequestParam Map<String,String> params,
                           Pageable pageable) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        Page<Car> cars = carService.getAllCars(pageable,params);
        responseMap.put("content", cars);
        responseMap.put("totalElements", cars.getContent().size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) throws CarException {
        return carService.getCar(id);
    }


    @PostMapping()
    public String addCar(@RequestBody @Valid CarDto carDto) {
        Car car = carService.createCar(carDto);
        return "/api/cars/" + car.getId();
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

}