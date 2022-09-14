package by.singularity.mapper.impl;

import by.singularity.dto.StorageDto;
import by.singularity.entity.Storage;
import by.singularity.mapper.Mapper;
import by.singularity.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StorageMapper implements Mapper<Storage, StorageDto> {
    private final ModelMapper mapper;
    private final ClientRepository clientRepository;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Storage.class, StorageDto.class)
                .addMappings(m->m.skip(StorageDto::setClientId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(StorageDto.class, Storage.class)
                .addMappings(m->m.skip(Storage::setClient))
                .setPostConverter(toModelConverter());
    }

    @Override
    public Storage toModel(StorageDto storageDto) {
        return Objects.isNull(storageDto) ? null : mapper.map(storageDto, Storage.class);
    }

    @Override
    public StorageDto toDto(Storage storage) {
        return Objects.isNull(storage) ? null : mapper.map(storage, StorageDto.class);
    }

    private Converter<StorageDto, Storage> toModelConverter() {
        return context -> {
            StorageDto source = context.getSource();
            Storage destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<Storage, StorageDto> toDtoConverter() {
        return context -> {
            Storage source = context.getSource();
            StorageDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(Storage source, StorageDto destination) {
        destination.setClientId(source.getClient().getId());
    }

    private void mapSpecificFields(StorageDto source, Storage destination) {
        destination.setClient(clientRepository.findById(source.getClientId()).orElse(null));
    }
}
