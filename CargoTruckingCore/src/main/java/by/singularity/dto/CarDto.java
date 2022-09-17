package by.singularity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto implements Serializable {
    private Long id;

    @Length(min = 1)
    @NotNull
    @NotEmpty
    private String brand;

    @NotNull
    @NotEmpty
    @Length(min = 1)
    private String carNumber;

    @NotNull
    @Min(1)
    private Integer priceForKm;

    @NotNull
    @Min(1)
    private Integer consumptionForKm;
}
