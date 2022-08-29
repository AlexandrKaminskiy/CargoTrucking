package by.singularity.dto;

import by.singularity.entity.CarriageStatus;
import by.singularity.entity.Checkpoint;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class WayBillDto implements Serializable {
    @Min(1)
    private final Long invoiceId;
    @Min(1)
    private final Integer distance;
    @Min(1)
    private final Long carId;
    @DateTimeFormat
    private final Date endDate;
    @Min(1)
    private final Long verifierId;
    private final Set<CarriageStatus> carriageStatuses;
    private final Set<Checkpoint> checkpoints;
}
