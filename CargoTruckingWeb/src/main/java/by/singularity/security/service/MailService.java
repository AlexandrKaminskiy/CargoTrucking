package by.singularity.security.service;

import by.singularity.entity.User;
import by.singularity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
@RequestMapping("/api/email")
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender emailSender;
    private final UserRepository userRepository;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (Exception e) {
            log.info("ERROR SENDING MESSAGE");
        }
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void sendCelebration() {
        log.info("CELEBRATION TIME");
        Calendar today = Calendar.getInstance();
        int day = today.get(Calendar.DAY_OF_YEAR);
        Iterable<User> users = userRepository.findAll();
        users.forEach(user -> {
            try {
                Calendar bornDate = new GregorianCalendar();
                bornDate.setTime(user.getBornDate());
                if (bornDate.get(Calendar.DAY_OF_YEAR) == day) {
                    sendCelebrationMessage(user.getEmail(), user.getName());
                }
            } catch (Exception e) {
            }
        });
    }

    private void sendCelebrationMessage(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        String subject = "Happy birthday!";
        String text = name + ", happy Birthday! May God bless you with everything you desire. Many many happy returns of the day.\n" +
                "Sincerely, Stone Cargo trucking";
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
