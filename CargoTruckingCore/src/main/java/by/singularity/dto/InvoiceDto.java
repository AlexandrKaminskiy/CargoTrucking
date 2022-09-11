package by.singularity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto implements Serializable {
    @Length(max = 20, min = 1)
    @NotNull
    @NotEmpty
    private String number;

    @Min(1)
    @NotNull
    @NotEmpty
    private Long storageId;

    @Min(1)
    @NotNull
    @NotEmpty
    private Long creatorId;

    private Long productOwnerId;

    @Min(1)
    @NotNull
    @NotEmpty
    private Long driverId;

    private Set<ProductDto> products;
}
