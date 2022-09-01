package by.singularity.controller;

import by.singularity.dto.UserDto;
import by.singularity.pojo.PasswordChanger;
import by.singularity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping()
    public UserDto getUser(HttpServletRequest request) {
        return userService.getUserAuthInfo(request);
    }

    @PutMapping()
    public void alterUser( HttpServletRequest request, @RequestBody UserDto userDto) {
        userService.alterUserAuthInfo(request, userDto);
    }

    @PutMapping("/change-password")
    public void changePassword(HttpServletRequest request, @RequestBody PasswordChanger passwordChanger) {
        userService.changePassword(request, passwordChanger);
    }
}