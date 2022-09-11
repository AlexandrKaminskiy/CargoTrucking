package by.singularity.service;

import by.singularity.dto.ClientDto;
import by.singularity.dto.UserDto;
import by.singularity.entity.Client;
import by.singularity.entity.ClientStatus;
import by.singularity.entity.QClient;
import by.singularity.entity.User;
import by.singularity.exception.ClientException;
import by.singularity.exception.UserException;
import by.singularity.mapper.ClientMapper;
import by.singularity.repository.ClientRepository;
import by.singularity.repository.queryUtils.QPredicate;
import by.singularity.service.utils.ParseUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserService userService;

    public Page<Client> getAllClients(Pageable pageable, Map<String,String> params) {
        return clientRepository.findAll(getFindingPredicate(params), pageable);
    }

    @Transactional
    public Long createClient(ClientDto clientDto) throws UserException, ClientException {
        UserDto userDto = clientDto.getAdminInfo();
        if (clientRepository.existsById(userDto.getClientId())) {
            log.error("CLIENT WITH THIS ID IS EXIST, ERROR DURING CREATION");
            throw new ClientException("client with this id is exists");
        }
        User user = userService.registerUser(clientDto.getAdminInfo());
        Client client = clientMapper.toModel(clientDto);
        client.setIsActive(false);
        client.setId(userDto.getClientId());
        client.setAdminInfo(user);
        clientRepository.save(client);
        log.info("CLIENT {} CREATED", client.getName());
        return client.getId();
    }

    @Transactional
    public void updateClient(ClientDto clientDto, Long id) throws ClientException {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isEmpty()) {
            throw new ClientException("client with this id is exists");
        }
        Client client = clientOpt.get();
        Optional.ofNullable(clientDto.getName()).ifPresent(client::setName);
        Optional.ofNullable(clientDto.getStatus()).ifPresent(client::setStatus);
        clientRepository.save(client);
        log.info("CLIENT {} UPDATED", client.getName());
    }

    public Client getClient(Long id) throws ClientException {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isEmpty()) {
            throw new ClientException("client with id " + id + " not found");
        }
        return clientOpt.get();
    }

    public void activateClient(Long id) {
        clientRepository.findById(id).ifPresent(client -> client.setIsActive(true));
        log.info("CLIENT WITH ID {} ACTIVATED", id);
    }

    public void deleteClient(Long id) {
        clientRepository.findById(id).ifPresent(client -> client.setIsActive(false));
        log.info("CLIENT WITH ID {} DELETED", id);
    }

    private Predicate getFindingPredicate(Map<String,String> params) {
        return QPredicate.builder()
                .add(params.get("name"), QClient.client.name::eq)
                .add(ParseUtils.parseBool(params.get("name")), QClient.client.isActive::eq)
                .add(ParseUtils.parseEnum(params.get("status"), ClientStatus.class),QClient.client.status::contains)
                .buildAnd();
    }
}
