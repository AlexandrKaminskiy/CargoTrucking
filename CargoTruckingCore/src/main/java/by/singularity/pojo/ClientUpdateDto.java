package by.singularity.pojo;

import by.singularity.entity.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateDto {

    @Length(max = 30)
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private Set<ClientStatus> status;
}
