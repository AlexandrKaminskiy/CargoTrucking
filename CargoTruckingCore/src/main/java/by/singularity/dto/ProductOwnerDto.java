package by.singularity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class ProductOwnerDto implements Serializable {
    @Length(min = 1, max = 40)
    private String name;
    private Set<ProductDto> products;
}
