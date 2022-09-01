package by.singularity.dto;

import by.singularity.entity.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class ClientDto implements Serializable {
    @Length(max = 30, min = 1)
    private String name;
    private Set<ClientStatus> status;
    private UserDto adminInfo;
}
