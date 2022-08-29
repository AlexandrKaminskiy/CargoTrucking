package by.singularity.mapper.impl;

import by.singularity.dto.CheckpointDto;
import by.singularity.entity.Checkpoint;
import by.singularity.mapper.CheckpointMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.Date;

@Component
public class CheckpointMapperImpl implements CheckpointMapper {

    @Override
    public Checkpoint toModel(CheckpointDto checkpointDto) {
        if ( checkpointDto == null ) {
            return null;
        }

        Checkpoint checkpoint = new Checkpoint();

        checkpoint.setAddress( checkpointDto.getAddress() );
        checkpoint.setRequiredArrivalDate( checkpointDto.getRequiredArrivalDate() );
        checkpoint.setCheckpointDate( checkpointDto.getCheckpointDate() );

        return checkpoint;
    }

    @Override
    public CheckpointDto toDto(Checkpoint checkpoint) {
        if ( checkpoint == null ) {
            return null;
        }

        String address = checkpoint.getAddress();
        Date requiredArrivalDate = checkpoint.getRequiredArrivalDate();
        Date checkpointDate = checkpoint.getCheckpointDate();

        CheckpointDto checkpointDto = new CheckpointDto( address, requiredArrivalDate, checkpointDate );

        return checkpointDto;
    }
}
