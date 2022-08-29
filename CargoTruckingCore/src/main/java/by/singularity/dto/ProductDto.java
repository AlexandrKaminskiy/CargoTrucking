package by.singularity.dto;

import by.singularity.entity.ProductStatus;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Set;

@Data
public class ProductDto implements Serializable {
    private final Long id;
    @Length(min = 1, max = 50)
    private final String name;
    @Min(1)
    private final Integer amount;
    @Min(1)
    private final Long creatorId;
    private final Set<ProductStatus> productStatus;
}
