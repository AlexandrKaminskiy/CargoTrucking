package by.singularity.controller;

import by.singularity.dto.UserDto;
import by.singularity.entity.User;
import by.singularity.exception.UserException;
import by.singularity.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public void getAll(HttpServletResponse response) throws IOException {
        Map<String,Object> responseMap = new HashMap<>();
        List<User> userList = userService.getAllUsers();
        responseMap.put("content",userList);
        responseMap.put("totalElements", userList.size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) throws UserException {
        return userService.getById(id);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody @Valid UserDto userDto) throws UserException {
        userService.updateUser(userDto);
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid UserDto userDto) throws UserException {
        //todo
        return "api/user/" + userService.registerUser(userDto).getId();
    }

    //todo исправить
    @DeleteMapping("/{id}")
    public void deleteUsers(@PathVariable Long id) {
        userService.deleteUsers(id);
    }
}
