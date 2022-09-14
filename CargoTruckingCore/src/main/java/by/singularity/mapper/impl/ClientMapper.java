package by.singularity.mapper.impl;

import by.singularity.dto.ClientDto;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.Client;
import by.singularity.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ClientMapper implements Mapper<Client, ClientDto> {
    private final ModelMapper mapper;

    @Override
    public Client toModel(ClientDto clientDto) {
        return Objects.isNull(clientDto) ? null : mapper.map(clientDto, Client.class);
    }

    @Override
    public ClientDto toDto(Client client) {
        return null;
    }
}
