package by.singularity.mapper.impl;

import by.singularity.dto.UserDto;
import by.singularity.entity.Client;
import by.singularity.entity.Role;
import by.singularity.entity.User;
import by.singularity.mapper.UserMapper;
import by.singularity.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final ClientRepository clientRepository;

    @Override
    public User toModel(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }
        User user = new User();
        user.setId( userDto.getId() );
        user.setName( userDto.getName() );
        user.setSurname( userDto.getSurname() );
        user.setPatronymic( userDto.getPatronymic() );
        user.setEmail( userDto.getEmail() );
        user.setTown( userDto.getTown() );
        user.setStreet( userDto.getStreet() );
        user.setHouse( userDto.getHouse() );
        user.setFlat( userDto.getFlat() );
        user.setLogin( userDto.getLogin() );
        user.setPassword( userDto.getPassword() );
        user.setPassportNum( userDto.getPassportNum() );
        user.setIssuedBy( userDto.getIssuedBy() );
        Set<Role> set = userDto.getRoles();
        if ( set != null ) {
            user.setRoles( new LinkedHashSet<>( set ) );
        }

        return user;
    }

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        Set<Role> roles = null;
        Long id;
        String name;
        String surname;
        String patronymic;
        String email;
        String town;
        String street;
        Integer house;
        Integer flat;
        String login;
        String password;
        String passportNum;
        String issuedBy;
        Long clientId;

        Set<Role> set = user.getRoles();
        if ( set != null ) {
            roles = new LinkedHashSet<>( set );
        }
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        patronymic = user.getPatronymic();
        email = user.getEmail();
        town = user.getTown();
        street = user.getStreet();
        house = user.getHouse();
        flat = user.getFlat();
        login = user.getLogin();
        password = user.getPassword();
        passportNum = user.getPassportNum();
        issuedBy = user.getIssuedBy();
        Date bornDate = user.getBornDate();

        return new UserDto( id, name, surname, patronymic, user.getClient().getId(), bornDate, email, town, street, house, flat, login, password, passportNum, issuedBy, roles );
    }
}
