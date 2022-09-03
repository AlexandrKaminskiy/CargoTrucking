package by.singularity.controller;

import by.singularity.dto.ClientDto;
import by.singularity.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping()
    public List<ClientDto> getAll() {
        return clientService.getAllClients();
    }

    @PostMapping()
    public String addClient(@RequestBody @Valid ClientDto clientDto) {
        Long createdId = clientService.createClient(clientDto);
        return "/api/clients/" + createdId;
    }

    @PutMapping("/{id}")
    public void updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        clientService.updateClient(clientDto, id);

    }
}
