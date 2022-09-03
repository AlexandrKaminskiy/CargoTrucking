package by.singularity.dto;

import by.singularity.entity.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto implements Serializable {
    @Length(max = 30, min = 1)
    private String name;
    @NotNull
    private Set<ClientStatus> status;
    @NotNull
    @Valid
    private UserDto adminInfo;
}
