package by.singularity.controller;

import by.singularity.exception.UserException;
import by.singularity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restore")
@RequiredArgsConstructor
public class RestoreController {
    private final UserService userService;

    @GetMapping("/{uuid}")
    public String restorePassword(@PathVariable String uuid,
                                  @RequestParam String password) throws UserException {
        System.out.println(password);
        return userService.restorePassword(uuid, password);
    }
}
