package by.singularity.dto;

import by.singularity.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
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
    private String name;

    @Min(1)
    @NotNull
    private Integer amount;

    @NotNull
    private Set<ProductStatus> productStatus;

}
