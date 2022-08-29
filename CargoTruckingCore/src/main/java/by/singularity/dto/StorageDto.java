package by.singularity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class StorageDto implements Serializable {
    private final Long id;
    @Length(max = 20, min = 1)
    private final String name;
    @Length(max = 20, min = 1)
    private final String address;
    @Min(1)
    private final Long clientId;
}
