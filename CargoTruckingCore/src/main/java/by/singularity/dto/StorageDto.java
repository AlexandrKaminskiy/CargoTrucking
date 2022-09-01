package by.singularity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class StorageDto implements Serializable {
    private Long id;
    @Length(max = 20, min = 1)
    private String name;
    @Length(max = 20, min = 1)
    private String address;
    @Min(1)
    private Long clientId;
}
