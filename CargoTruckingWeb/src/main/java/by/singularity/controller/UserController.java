package by.singularity.controller;

import by.singularity.dto.UserDto;
import by.singularity.entity.User;
import by.singularity.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

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
    public User getById(@PathVariable Long id) throws IOException {
        return userService.getById(id);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody @Valid UserDto userDto) {
        userService.updateUser(userDto);
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid UserDto userDto) {
        return userService.registerUser(userDto);
    }

    //todo исправить
    @DeleteMapping("/{id}")
    public void deleteUsers(@PathVariable Long id) {
        userService.deleteUsers(id);
    }
}
