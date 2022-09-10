package by.singularity.controller;

import by.singularity.exception.CustomException;
import by.singularity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/delete")
    String delete(){
        productRepository.deleteById(2L);
        return "oke";
    }

    @GetMapping()
    int fun(boolean invalid) throws CustomException {
        if (invalid) {
            throw new CustomException("ga-ga-ga");
        }
        return 7;
    }
}
