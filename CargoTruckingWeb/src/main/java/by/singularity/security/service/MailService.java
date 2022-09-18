package by.singularity.security.service;

import by.singularity.entity.QUser;
import by.singularity.entity.RepairingMessage;
import by.singularity.entity.User;
import by.singularity.exception.UserException;
import by.singularity.pojo.MailParams;
import by.singularity.repository.RepairingMailRepository;
import by.singularity.repository.UserRepository;
import by.singularity.repository.queryUtils.QPredicate;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

@Component
@RequestMapping("/api/email")
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private final RepairingMailRepository repairingMailRepository;
    private final int HOUR = 3600000;
    public void sendSimpleMessage(MailParams mailParams) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailParams.getTo());
            message.setSubject(mailParams.getSubject());
            message.setText(mailParams.getText());
            emailSender.send(message);
        } catch (Exception e) {
            log.info("ERROR SENDING MESSAGE");
        }
    }

    @Scheduled(cron = "0 0 7 * * *")
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

    public String updatePassword(MailParams mailParams) throws UserException {
        if (!userRepository.exists(getFindingPredicate(mailParams.getTo()))) {
            return "this email not exists";
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailParams.getTo());
            String uuid = UUID.randomUUID().toString();
            RepairingMessage repairingMessage = new RepairingMessage();
            repairingMessage.setEmail(mailParams.getTo());
            repairingMessage.setExpirationTime(System.currentTimeMillis() + HOUR);
            repairingMessage.setUuid(uuid);
            repairingMailRepository.save(repairingMessage);
            message.setSubject("Your repairing token");
            message.setText(uuid);
            emailSender.send(message);
            return "message was sent to your email";
        } catch (Exception e) {
            log.info("ERROR SENDING MESSAGE");
        }
        return "sending message error";
    }



    private Predicate getFindingPredicate(String email) {
        return QPredicate.builder()
                .add(email, QUser.user.email::eq)
                .buildAnd();
    }
}
