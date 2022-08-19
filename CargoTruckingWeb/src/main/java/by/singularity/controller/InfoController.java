package by.singularity.controller;

import by.singularity.entity.Client;
import by.singularity.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InfoController {

    private final ClientService clientService;

    @GetMapping("/about")
    public String hello(){
        return "about";
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.findClient("sanya");
    }
}


