package by.singularity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apii")
@RequiredArgsConstructor
public class InfoController {

//    private final UserService clientService;

    @GetMapping("/about")
    public String hello(){
        return "about";
    }

    @GetMapping
    public String getClients() {
        return "sanya";
    }
}


