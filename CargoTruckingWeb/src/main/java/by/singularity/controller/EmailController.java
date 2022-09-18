package by.singularity.controller;

import by.singularity.exception.UserException;
import by.singularity.pojo.MailParams;
import by.singularity.security.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final MailService mailService;

    @PostMapping
    public void sendSimpleMessage(@RequestBody @Valid MailParams mailParams) {
        mailService.sendSimpleMessage(mailParams);
    }

    @PostMapping("/repairing")
    public String sendRestoringMessage(@RequestBody @Valid MailParams mailParams) throws UserException {
        return mailService.updatePassword(mailParams);
    }


}
