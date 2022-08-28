package by.singularity.controller;

import by.singularity.repository.impl.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping()
    String delete(){
        productRepository.deleteById(2L);
        return "oke";
    }
}
