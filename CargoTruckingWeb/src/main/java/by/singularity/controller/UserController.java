package by.singularity.controller;

import by.singularity.dto.UserDto;
import by.singularity.entity.User;
import by.singularity.exception.UserException;
import by.singularity.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public void getAll(@RequestParam Map<String, String> params,
                       Pageable pageable,
                       HttpServletResponse response) throws IOException {
        Map<String,Object> responseMap = new HashMap<>();
        Page<User> userPage = userService.getAllUsers(pageable,params);
        responseMap.put("content",userPage);
        responseMap.put("totalElements", userPage.getContent().size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) throws UserException {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@RequestBody @Valid UserDto userDto,
                           @PathVariable Long id) throws UserException {
        userService.updateUser(userDto, id);
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid UserDto userDto) throws UserException {
        return "api/user/" + userService.registerUser(userDto).getId();
    }


    @DeleteMapping("/{id}")
    public void deleteUsers(@PathVariable Long id) {
        userService.deleteUsers(id);
    }
}
