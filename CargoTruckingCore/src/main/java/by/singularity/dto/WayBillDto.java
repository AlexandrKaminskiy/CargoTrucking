package by.singularity.dto;

import by.singularity.entity.CarriageStatus;
import by.singularity.entity.Checkpoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WayBillDto implements Serializable {
    @Length(min = 1)
    private String invoiceId;
    @Min(1)
    private Integer distance;
    @Min(1)
    private Long carId;
    @DateTimeFormat
    private Date endDate;
    @Min(1)
    private Long verifierId;
    private Set<CarriageStatus> carriageStatuses;
    private Set<Checkpoint> checkpoints;
}
