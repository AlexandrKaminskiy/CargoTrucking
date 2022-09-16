package by.singularity.dto;

import by.singularity.entity.CarriageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WayBillDto implements Serializable {
    private Long id;

    @NotNull
    @NotEmpty
    private String invoiceNumber;

    @Min(1)
    @NotNull
    private Integer distance;

    @Min(1)
    @NotNull
    private Long carId;

    @DateTimeFormat
    @NotNull
    private Date endDate;

    private Set<CarriageStatus> carriageStatuses;

    @NotNull
    @Valid
    @Size(min = 1, message = "min checkpoints quantity is 1")
    private Set<CheckpointDto> checkpoints;
}
