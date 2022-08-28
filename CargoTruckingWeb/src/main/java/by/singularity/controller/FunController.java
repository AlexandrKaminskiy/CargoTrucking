package by.singularity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leshik")
public class FunController {
    @GetMapping
    public String show() {
        return "babuba";
    }
}
