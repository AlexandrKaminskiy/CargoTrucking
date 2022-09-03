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
    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserMapperImpl userMapper;

    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map((clientMapper::toDto))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createClient(ClientDto clientDto) {
        UserDto userDto = clientDto.getAdminInfo();
        String login = userDto.getLogin();
        String email = userDto.getEmail();
        if (userRepository.findByLogin(login).isPresent()) {
            //todo убрать null
            log.error("USER WITH LOGIN {} EXISTS, ERROR DURING CREATION", login);
            return null;
        }
        if (userRepository.findByEmail(email).isPresent()) {
            log.error("USER WITH EMAIL {} EXISTS, ERROR DURING CREATION", email);
            return null;
        }
        Set<Role> userRoles = userDto.getRoles();
        if (userRoles.size() == 0) {
            log.error("USER HASN'T ROLES, ERROR DURING CREATION");
            return null;
        }
        if (clientRepository.findById(userDto.getClientId()).isPresent()) {
            log.error("CLIENT WITH THIS ID IS EXIST, ERROR DURING CREATION");
            return null;
        }
        User user = userMapper.toModel(userDto);
        log.info("USER {} CREATED", user.getLogin());
        Client client = clientMapper.toModel(clientDto);
        client.setId(userDto.getClientId());
        client.setAdminInfo(null);
        clientJpaRepository.save(client);
        userJpaRepository.save(user);
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
}
