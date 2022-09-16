package by.singularity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckpointDto implements Serializable {
    private Long id;

    @Length(min = 1)
    @NotNull
    @NotEmpty
    private String address;

    @DateTimeFormat
    @NotNull
    private Date requiredArrivalDate;
}
