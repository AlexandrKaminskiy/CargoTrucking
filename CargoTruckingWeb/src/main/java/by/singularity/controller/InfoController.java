package by.singularity.controller;

import by.singularity.entity.Client;
import by.singularity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/apii")
@RequiredArgsConstructor
public class InfoController {

    private final UserService clientService;

    @GetMapping("/about")
    public String hello(){
        return "about";
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.findUser("sanya");
    }
}


