package by.singularity.service;

import by.singularity.dto.ClientDto;
import by.singularity.entity.Client;
import by.singularity.mapper.ClientMapper;
import by.singularity.repository.impl.ClientRepository;
import by.singularity.repository.jparepo.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientJpaRepository clientJpaRepository;
    private final ClientMapper clientMapper;

    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map((clientMapper::toDto))
                .collect(Collectors.toList());
    }


}
