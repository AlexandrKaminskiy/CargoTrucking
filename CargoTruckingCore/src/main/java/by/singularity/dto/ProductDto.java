package by.singularity.dto;

import by.singularity.entity.ProductStatus;
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
public class ProductDto implements Serializable {
    private Long id;

    @Length(min = 1, max = 50)
    @NotNull
    @NotEmpty
    private String name;

    @Min(1)
    @NotNull
    @NotEmpty
    private Integer amount;

    @Min(1)
    @NotNull
    @NotEmpty
    private Long creatorId;

    private Set<ProductStatus> productStatus;
}
