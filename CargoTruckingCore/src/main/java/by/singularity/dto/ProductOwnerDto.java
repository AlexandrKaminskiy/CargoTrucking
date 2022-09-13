package by.singularity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOwnerDto implements Serializable {
    @Length(min = 1, max = 40)
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Valid
    private Set<ProductDto> products;
}
