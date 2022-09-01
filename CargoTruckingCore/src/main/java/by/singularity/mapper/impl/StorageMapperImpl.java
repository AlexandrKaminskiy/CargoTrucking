package by.singularity.mapper.impl;

import by.singularity.dto.StorageDto;
import by.singularity.entity.Storage;
import by.singularity.mapper.StorageMapper;
import by.singularity.repository.impl.ClientRepository;
import by.singularity.repository.impl.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;

@Component
@RequiredArgsConstructor
public class StorageMapperImpl implements StorageMapper {
    private final ClientRepository clientRepository;

    @Override
    public Storage toModel(StorageDto storageDto) {
        if ( storageDto == null ) {
            return null;
        }

        Storage storage = new Storage();

        storage.setId( storageDto.getId() );
        storage.setName( storageDto.getName() );
        storage.setAddress( storageDto.getAddress() );
        storage.setClient(clientRepository.findById(storageDto.getClientId()).get());
        return storage;
    }

    @Override
    public StorageDto toDto(Storage storage) {
        if ( storage == null ) {
            return null;
        }

        Long id;
        String name;
        String address;

        id = storage.getId();
        name = storage.getName();
        address = storage.getAddress();

        Long clientId = storage.getClient().getId();

        return new StorageDto( id, name, address, clientId );
    }
}
