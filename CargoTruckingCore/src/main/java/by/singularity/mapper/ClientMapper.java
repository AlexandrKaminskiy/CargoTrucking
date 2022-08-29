package by.singularity.mapper;

import by.singularity.dto.ClientDto;
import by.singularity.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toModel(ClientDto clientDto);
    ClientDto toDto(Client client);
}
