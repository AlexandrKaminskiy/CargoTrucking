package by.singularity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Set;

@Data
public class InvoiceDto implements Serializable {
    @Length(max = 20, min = 1)
    private final String number;
    @Min(1)
    private final Long storageId;
    @Min(1)
    private final Long creatorId;
    @Min(1)
    private final Long driverId;
    private final Set<ProductDto> products;
}
