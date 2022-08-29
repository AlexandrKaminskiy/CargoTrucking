package by.singularity.dto;

import by.singularity.entity.ClientStatus;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.io.Serializable;
import java.util.Set;



@Data
public class ClientDto implements Serializable {
    @Length(max = 30, min = 1)
    private final String name;

    private final Set<ClientStatus> status;
    private final UserDto adminInfo;
}
