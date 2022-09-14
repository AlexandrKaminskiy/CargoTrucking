package by.singularity.service;

import by.singularity.dto.CheckpointDto;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.WayBill;
import by.singularity.mapper.impl.CheckpointMapper;
import by.singularity.repository.CheckpointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckpointService {
    private final CheckpointMapper checkpointMapper;
    private final CheckpointRepository checkpointRepository;
    public Checkpoint createCheckpoint(CheckpointDto checkpointDto, WayBill wayBill) {
        Checkpoint checkpoint = checkpointMapper.toModel(checkpointDto);
        checkpoint.setWayBill(wayBill);
        checkpointRepository.save(checkpoint);
        log.info("CHECKPOINT WITH ID {} CREATED", checkpoint.getId());
        return checkpoint;
    }
}
