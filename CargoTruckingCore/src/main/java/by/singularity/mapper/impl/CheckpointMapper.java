package by.singularity.mapper.impl;

import by.singularity.dto.CheckpointDto;
import by.singularity.entity.Checkpoint;
import by.singularity.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CheckpointMapper implements Mapper<Checkpoint, CheckpointDto> {
    private final ModelMapper mapper;

    @Override
    public Checkpoint toModel(CheckpointDto checkpointDto) {
        return Objects.isNull(checkpointDto) ? null : mapper.map(checkpointDto, Checkpoint.class);
    }

    @Override
    public CheckpointDto toDto(Checkpoint checkpoint) {
        return Objects.isNull(checkpoint) ? null : mapper.map(checkpoint, CheckpointDto.class);
    }
}
