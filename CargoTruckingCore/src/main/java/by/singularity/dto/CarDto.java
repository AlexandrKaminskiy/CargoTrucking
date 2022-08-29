package by.singularity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class CarDto implements Serializable {
    private final Long id;
    @Length(min = 1)
    private final String brand;
    @Length(min = 1)
    private final String carNumber;
}
