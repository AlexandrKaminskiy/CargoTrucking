package by.singularity.mapper.impl;

import by.singularity.dto.ClientDto;
import by.singularity.dto.UserDto;
import by.singularity.entity.Client;
import by.singularity.entity.ClientStatus;
import by.singularity.entity.Role;
import by.singularity.entity.User;
import by.singularity.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class ClientMapperImpl implements ClientMapper {

    private final UserMapperImpl userMapper;
    @Override
    public Client toModel(ClientDto clientDto) {
        if ( clientDto == null ) {
            return null;
        }

        Client client = new Client();

        client.setName( clientDto.getName() );
        Set<ClientStatus> set = clientDto.getStatus();
        if ( set != null ) {
            client.setStatus(new LinkedHashSet<>(set) );
        }
        client.setAdminInfo( userMapper.toModel( clientDto.getAdminInfo()));

        return client;
    }

    @Override
    public ClientDto toDto(Client client) {
        if ( client == null ) {
            return null;
        }

        Set<ClientStatus> status = null;
        String name;
        UserDto adminInfo;

        Set<ClientStatus> set = client.getStatus();
        if ( set != null ) {
            status = new LinkedHashSet<>(set);
        }
        name = client.getName();
        adminInfo = userMapper.toDto(client.getAdminInfo());
        return new ClientDto( name, status, adminInfo );
    }


}
