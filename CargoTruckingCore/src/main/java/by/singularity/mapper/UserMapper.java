package by.singularity.mapper;

import by.singularity.dto.ClientDto;
import by.singularity.dto.UserDto;
import by.singularity.entity.Client;
import by.singularity.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toModel(UserDto userDto);
    UserDto toDto(User user);
}
