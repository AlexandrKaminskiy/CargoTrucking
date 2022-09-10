package by.singularity.controller;

import by.singularity.dto.CarDto;
import by.singularity.dto.InvoiceDto;
import by.singularity.entity.Car;
import by.singularity.entity.Invoice;
import by.singularity.exception.CarException;
import by.singularity.exception.InvoiceException;
import by.singularity.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    //todo add pagination
    @GetMapping()
    public void getAllCars(HttpServletResponse response) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        List<Car> cars = carService.getAllCars();
        responseMap.put("content", cars);
        responseMap.put("totalElements", cars.size());
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