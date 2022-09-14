package by.singularity.mapper.impl;

import by.singularity.dto.UserDto;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.User;
import by.singularity.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDto> {
    private final ModelMapper mapper;

    @Override
    public User toModel(UserDto userDto) {
        return Objects.isNull(userDto) ? null : mapper.map(userDto, User.class);
    }

    @Override
    public UserDto toDto(User user) {
        return Objects.isNull(user) ? null : mapper.map(user, UserDto.class);
    }
}
