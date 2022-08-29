package by.singularity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Set;

@Data
public class ProductOwnerDto implements Serializable {
    @Length(min = 1, max = 40)
    private final String name;
    private final Set<ProductDto> products;
}
