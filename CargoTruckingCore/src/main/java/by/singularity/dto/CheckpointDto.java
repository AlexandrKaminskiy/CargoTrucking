package by.singularity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckpointDto implements Serializable {
    @Length(min = 1)
    private String address;
    @DateTimeFormat
    private Date requiredArrivalDate;
    @DateTimeFormat
    private Date checkpointDate;
}
