package by.singularity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageDto implements Serializable {
    private Long id;

    @Length(max = 20, min = 1)
    @NotNull
    @NotEmpty
    private String name;

    @Length(max = 20, min = 1)
    @NotNull
    @NotEmpty
    private String address;

    @Min(1)
    @NotNull
    @NotEmpty
    private Long clientId;
}
