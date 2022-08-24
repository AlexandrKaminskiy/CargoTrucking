package by.singularity.service;

import by.singularity.entity.Client;
import by.singularity.repository.ClientJpaRepository;
import by.singularity.repository.impl.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ClientRepository userRepository;
    private final ClientJpaRepository userJpaRepository;


    public List<Client> findUser(String name){
        Client user = new Client();
        user.setName("sanya");
        user.setDate(new Date(2003, Calendar.AUGUST,4));
        user.setEmail("sasha.pinsk2003@gmail.com");
        user.setFlat(4);
        user.setHouse(3);
        user.setIssuedBy("2dewfvsdv");
        user.setLogin("1232edwq");
        user.setPassportNum("214wqsas");
        user.setStreet("2edeadscdc");
        user.setPatronymic("wfdcdsvvvsd");
        user.setPassword("efwgrsfbfdb");
        user.setTown("ewfvsdvsdvsdv");

        userJpaRepository.save(user);

//        return customClientRepository.findByIdAndName(id, name);
        return userRepository.findByName(name);
    }
}
