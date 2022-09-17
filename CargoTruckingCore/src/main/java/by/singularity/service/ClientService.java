package by.singularity.service;

import by.singularity.dto.ClientDto;
import by.singularity.dto.UserDto;
import by.singularity.entity.Client;
import by.singularity.entity.ClientStatus;
import by.singularity.entity.QClient;
import by.singularity.entity.User;
import by.singularity.exception.ClientException;
import by.singularity.exception.UserException;
import by.singularity.mapper.impl.ClientMapper;
import by.singularity.pojo.ClientUpdateDto;
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

import java.util.Date;
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
        if (userDto.getClientId() == null) {
            log.error("ID NOT PASSED, ERROR DURING CREATION");
            throw new ClientException("you should pass client id");
        }
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
    public void updateClient(ClientUpdateDto clientUpdateDto, Long id) throws ClientException {
        Client client = clientRepository.findById(id)
                .orElseThrow(()->new ClientException("client with this id is exists"));
        Optional.ofNullable(clientUpdateDto.getName()).ifPresent(client::setName);
        Optional.ofNullable(clientUpdateDto.getStatus()).ifPresent(client::setStatus);
        clientRepository.save(client);
        log.info("CLIENT {} UPDATED", client.getName());
    }

    public Client getClient(Long id) throws ClientException {
        return clientRepository.findById(id)
                .orElseThrow(()->new ClientException("client with id " + id + " not found"));
    }

    @Transactional
    public void activateClient(Long id) {
        clientRepository.findById(id).ifPresent(client -> {
            client.setIsActive(true);
            client.setActiveDate(new Date());
            clientRepository.save(client);
            log.info("CLIENT WITH ID {} ACTIVATED", id);
        });
    }

    @Transactional
    public void deleteClient(Long id) {
        clientRepository.findById(id).ifPresent(client -> {
            client.setIsActive(false);
            client.setActiveDate(new Date());
            clientRepository.save(client);
            log.info("CLIENT WITH ID {} DELETED", id);
        });

    }

    private Predicate getFindingPredicate(Map<String,String> params) {
        return QPredicate.builder()
                .add(params.get("name"), QClient.client.name::eq)
                .add(ParseUtils.parseBool(params.get("isActive")), QClient.client.isActive::eq)
                .add(ParseUtils.parseEnum(params.get("status"), ClientStatus.class),QClient.client.status::contains)
                .buildAnd();
    }
}
