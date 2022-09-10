package by.singularity.service;

import by.singularity.dto.CheckpointDto;
import by.singularity.entity.Checkpoint;
import by.singularity.mapper.CheckpointMapper;
import by.singularity.repository.CheckpointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckpointService {
    private final CheckpointMapper checkpointMapper;
    private final CheckpointRepository checkpointRepository;
    public Checkpoint createCheckpoint(CheckpointDto checkpointDto) {
        Checkpoint checkpoint = checkpointMapper.toModel(checkpointDto);
        checkpointRepository.save(checkpoint);
        return checkpoint;

    }
}
