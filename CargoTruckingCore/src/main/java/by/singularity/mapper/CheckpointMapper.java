package by.singularity.mapper;

import by.singularity.dto.CarDto;
import by.singularity.dto.CheckpointDto;
import by.singularity.entity.Car;
import by.singularity.entity.Checkpoint;
import org.mapstruct.Mapper;

@Mapper
public interface CheckpointMapper {
    Checkpoint toModel(CheckpointDto checkpointDto);
    CheckpointDto toDto(Checkpoint checkpoint);
}
