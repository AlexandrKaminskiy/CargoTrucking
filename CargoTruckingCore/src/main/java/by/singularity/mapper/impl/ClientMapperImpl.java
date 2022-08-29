package by.singularity.mapper.impl;

import by.singularity.dto.ClientDto;
import by.singularity.dto.UserDto;
import by.singularity.entity.Client;
import by.singularity.entity.ClientStatus;
import by.singularity.entity.Role;
import by.singularity.entity.User;
import by.singularity.mapper.ClientMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client toModel(ClientDto clientDto) {
        if ( clientDto == null ) {
            return null;
        }

        Client client = new Client();

        client.setName( clientDto.getName() );
        Set<ClientStatus> set = clientDto.getStatus();
        if ( set != null ) {
            client.setStatus( new LinkedHashSet<ClientStatus>( set ) );
        }
        client.setAdminInfo( userDtoToUser( clientDto.getAdminInfo() ) );

        return client;
    }

    @Override
    public ClientDto toDto(Client client) {
        if ( client == null ) {
            return null;
        }

        Set<ClientStatus> status = null;
        String name = null;
        UserDto adminInfo = null;

        Set<ClientStatus> set = client.getStatus();
        if ( set != null ) {
            status = new LinkedHashSet<ClientStatus>( set );
        }
        name = client.getName();
        adminInfo = userToUserDto( client.getAdminInfo() );

        ClientDto clientDto = new ClientDto( name, status, adminInfo );

        return clientDto;
    }

    protected User userDtoToUser(UserDto userDto) {
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

    protected UserDto userToUserDto(User user) {
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
