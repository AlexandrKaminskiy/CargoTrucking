package by.singularity.controller;

import by.singularity.security.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final MailService mailService;

    @PostMapping
    public void sendSimpleMessage(String to, String subject, String text) {
        mailService.sendSimpleMessage(to, subject, text);
    }


}
