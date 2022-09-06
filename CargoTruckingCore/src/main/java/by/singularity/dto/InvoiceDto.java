package by.singularity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto implements Serializable {
    @Length(max = 20, min = 1)
    private String number;
    @Min(1)
    private Long storageId;
    @Min(1)
    private Long creatorId;
    private Long productOwnerId;
    @Min(1)
    private Long driverId;
    private Set<ProductDto> products;
}
