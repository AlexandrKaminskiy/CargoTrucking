package by.singularity.dto;

import by.singularity.entity.CarriageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WayBillDto implements Serializable {
    @Length(min = 1)
    @NotNull
    @NotEmpty
    private String invoiceNumber;

    @Min(1)
    @NotNull
    @NotEmpty
    private Integer distance;

    @Min(1)
    @NotNull
    @NotEmpty
    private Long carId;

    @DateTimeFormat
    @NotNull
    @NotEmpty
    private Date endDate;

    @Min(1)
    @NotNull
    @NotEmpty
    private Long verifierId;

    private Set<CarriageStatus> carriageStatuses;
    private Set<CheckpointDto> checkpointDtos;
}
