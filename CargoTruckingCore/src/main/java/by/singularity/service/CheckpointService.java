package by.singularity.service;

import by.singularity.dto.CheckpointDto;
import by.singularity.entity.Checkpoint;
import by.singularity.mapper.CheckpointMapper;
import by.singularity.repository.jparepo.CheckpointJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckpointService {
    private final CheckpointMapper checkpointMapper;
    private final CheckpointJpaRepository checkpointJpaRepository;
    public Checkpoint createCheckpoint(CheckpointDto checkpointDto) {
        Checkpoint checkpoint = checkpointMapper.toModel(checkpointDto);
        checkpointJpaRepository.save(checkpoint);
        return checkpoint;

    }
}
