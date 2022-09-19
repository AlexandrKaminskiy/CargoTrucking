package by.singularity.service;

import by.singularity.dto.CheckpointDto;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.CheckpointStatus;
import by.singularity.exception.CheckpointException;
import by.singularity.mapper.impl.CheckpointMapper;
import by.singularity.repository.CheckpointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckpointService {
    private final CheckpointMapper checkpointMapper;
    private final CheckpointRepository checkpointRepository;

    @Transactional
    public Checkpoint createCheckpoint(CheckpointDto checkpointDto) {
        Checkpoint checkpoint = checkpointMapper.toModel(checkpointDto);
        checkpoint.setStatus(new HashSet<>(Collections.singleton(CheckpointStatus.IN_PROGRESS)));
        log.info("CHECKPOINT WITH ID {} CREATED", checkpoint.getId());
        return checkpoint;
    }

    @Transactional
    public void reachCheckpoint(Long id) throws CheckpointException {
        Checkpoint checkpoint = checkpointRepository.findById(id)
                .orElseThrow(()->new CheckpointException("checkpoint with id " + id + "not exist"));
        checkpoint.setStatus(new HashSet<>(Collections.singleton(CheckpointStatus.REACHED)));
        checkpointRepository.save(checkpoint);
    }

    public Checkpoint getById(Long id) throws CheckpointException {
        return checkpointRepository.findById(id)
                .orElseThrow(()->new CheckpointException("checkpoint with id " + id + "not exist"));
    }

}
