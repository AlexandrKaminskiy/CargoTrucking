package by.singularity.dto;

import by.singularity.entity.ProductStatus;
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
public class ProductDto implements Serializable {
    private Long id;
    @Length(min = 1, max = 50)
    private String name;
    @Min(1)
    private Integer amount;
    @Min(1)
    private Long creatorId;
    private Set<ProductStatus> productStatus;
}
