package by.singularity.controller;

import by.singularity.dto.ClientDto;
import by.singularity.entity.Client;
import by.singularity.exception.ClientException;
import by.singularity.exception.UserException;
import by.singularity.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping()
    public void getAll(HttpServletResponse response,
                       Pageable pageable,
                       @RequestParam Map<String,String> params) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        Page<Client> clients = clientService.getAllClients(pageable, params);
        responseMap.put("content", clients);
        responseMap.put("totalElements", clients.getContent().size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) throws ClientException {
        return clientService.getClient(id);
    }


    @PostMapping()
    public String addClient(@RequestBody @Valid ClientDto clientDto) throws UserException, ClientException {
        Long createdId = clientService.createClient(clientDto);
        return "/api/clients/" + createdId;
    }

    @PutMapping("/{id}")
    public void updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) throws ClientException {
        clientService.updateClient(clientDto, id);
    }

    @PutMapping("/activate/{id}")
    public void activateClient(@PathVariable Long id) {
        clientService.activateClient(id);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}