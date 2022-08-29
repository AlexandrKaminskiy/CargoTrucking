package by.singularity.mapper;

import by.singularity.dto.StorageDto;
import by.singularity.entity.Storage;
import org.mapstruct.Mapper;

@Mapper
public interface StorageMapper {
    Storage toModel(StorageDto storageDto);
    StorageDto toDto(Storage storage);
}
