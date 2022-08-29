package by.singularity.mapper.impl;

import by.singularity.dto.UserDto;
import by.singularity.entity.Role;
import by.singularity.entity.User;
import by.singularity.mapper.UserMapper;

import javax.annotation.Generated;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-30T01:49:14+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

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
            user.setRoles( new LinkedHashSet<Role>( set ) );
        }

        return user;
    }

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        Set<Role> roles = null;
        Long id = null;
        String name = null;
        String surname = null;
        String patronymic = null;
        String email = null;
        String town = null;
        String street = null;
        Integer house = null;
        Integer flat = null;
        String login = null;
        String password = null;
        String passportNum = null;
        String issuedBy = null;

        Set<Role> set = user.getRoles();
        if ( set != null ) {
            roles = new LinkedHashSet<Role>( set );
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

        Date bornDate = null;

        UserDto userDto = new UserDto( id, name, surname, patronymic, bornDate, email, town, street, house, flat, login, password, passportNum, issuedBy, roles );

        return userDto;
    }
}
