package by.singularity.service;

import by.singularity.entity.User;
import by.singularity.repository.UserJpaRepository;
import by.singularity.repository.impl.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;


    public List<User> findUser(String name){
        User user = new User();
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
