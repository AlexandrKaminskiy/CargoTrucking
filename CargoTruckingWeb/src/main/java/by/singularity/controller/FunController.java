package by.singularity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leshik")
public class FunController {
    @GetMapping
    public String show() {
        return "babuba";
    }
}
