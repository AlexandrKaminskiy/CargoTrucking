package by.singularity.service;

import by.singularity.entity.Client;
import by.singularity.entity.UserRole;
import by.singularity.repository.ClientJpaRepository;
import by.singularity.repository.CustomClientRepository;
import by.singularity.repository.impl.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientJpaRepository clientJpaRepository;


    public List<Client> findClient(String name){
        Client client = new Client();
        client.setName("sanya");
        client.setDate(new Date(2003, Calendar.AUGUST,4));
        client.setEmail("sasha.pinsk2003@gmail.com");
        client.setFlat(4);
        client.setHouse(3);
        client.setIssuedBy("2dewfvsdv");
        client.setLogin("1232edwq");
        client.setPassportNum("214wqsas");
        client.setStreet("2edeadscdc");
        client.setPatronymic("wfdcdsvvvsd");
        client.setPassword("efwgrsfbfdb");
        client.setTown("ewfvsdvsdvsdv");
        client.setUserRole(Collections.singleton(UserRole.ADMIN));

        clientJpaRepository.save(client);

//        return customClientRepository.findByIdAndName(id, name);
        return clientRepository.findByName(name);
    }
}
