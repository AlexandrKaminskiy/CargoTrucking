package by.singularity.mapper.impl;

import by.singularity.dto.StorageDto;
import by.singularity.entity.Storage;
import by.singularity.mapper.StorageMapper;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-30T01:49:14+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class StorageMapperImpl implements StorageMapper {

    @Override
    public Storage toModel(StorageDto storageDto) {
        if ( storageDto == null ) {
            return null;
        }

        Storage storage = new Storage();

        storage.setId( storageDto.getId() );
        storage.setName( storageDto.getName() );
        storage.setAddress( storageDto.getAddress() );

        return storage;
    }

    @Override
    public StorageDto toDto(Storage storage) {
        if ( storage == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String address = null;

        id = storage.getId();
        name = storage.getName();
        address = storage.getAddress();

        Long clientId = null;

        StorageDto storageDto = new StorageDto( id, name, address, clientId );

        return storageDto;
    }
}
