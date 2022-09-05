package by.singularity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto implements Serializable {
    private Long id;
    @Length(min = 1)
    private String brand;
    @Length(min = 1)
    private String carNumber;
}