package by.singularity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class CheckpointDto implements Serializable {
    @Length(min = 1)
    private final String address;
    @DateTimeFormat
    private final Date requiredArrivalDate;
    @DateTimeFormat
    private final Date checkpointDate;
}
