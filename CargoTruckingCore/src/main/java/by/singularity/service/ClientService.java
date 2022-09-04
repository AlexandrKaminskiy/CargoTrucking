package by.singularity.service;

import by.singularity.dto.ClientDto;
import by.singularity.dto.UserDto;
import by.singularity.entity.Client;
import by.singularity.entity.Role;
import by.singularity.entity.User;
import by.singularity.mapper.ClientMapper;
import by.singularity.mapper.impl.UserMapperImpl;
import by.singularity.repository.impl.ClientRepository;
import by.singularity.repository.impl.UserRepository;
import by.singularity.repository.jparepo.ClientJpaRepository;
import by.singularity.repository.jparepo.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientJpaRepository clientJpaRepository;
    private final ClientMapper clientMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserMapperImpl userMapper;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public Long createClient(ClientDto clientDto) {
        UserDto userDto = clientDto.getAdminInfo();
        String login = userDto.getLogin();
        String email = userDto.getEmail();
        Set<Role> userRoles = userDto.getRoles();
        if (userRoles.size() == 0) {
            log.error("USER HASN'T ROLES, ERROR DURING CREATION");
            return null;
        }
        if (clientRepository.findById(userDto.getClientId()).isPresent()) {
            log.error("CLIENT WITH THIS ID IS EXIST, ERROR DURING CREATION");
            return null;
        }
        User user = userService.registerUser(clientDto.getAdminInfo());
        Client client = clientMapper.toModel(clientDto);
        client.setIsActive(false);
        client.setId(userDto.getClientId());
        client.setAdminInfo(null);
        clientJpaRepository.save(client);
        userRepository.updateClientInfoById(client,user.getId());
        clientRepository.updateUserInfoById(user,client.getId());
        log.info("CLIENT {} CREATED", client.getName());
        return client.getId();
    }

    @Transactional
    public void updateClient(ClientDto clientDto, Long id) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isEmpty()) {
            return;
        }
        Client client = clientOpt.get();
        Optional.ofNullable(clientDto.getName()).ifPresent(client::setName);
        Optional.ofNullable(clientDto.getStatus()).ifPresent(client::setStatus);
        clientJpaRepository.save(client);
        log.info("CLIENT {} UPDATED", client.getName());
    }

    public Client getClient(Long id) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isEmpty()) {
            //todo
            return null;
        }
        return clientOpt.get();

    }

    public void activateClient(Long id) {
        clientRepository.findById(id).ifPresent(client -> client.setIsActive(true));
    }

    public void deleteClient(Long id) {
        clientRepository.findById(id).ifPresent(client -> client.setIsActive(false));
    }
}
