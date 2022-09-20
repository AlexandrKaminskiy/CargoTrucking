package by.singularity.service;

import by.singularity.entity.Payment;
import by.singularity.entity.User;
import by.singularity.entity.WayBill;
import by.singularity.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public void createPayment(WayBill wayBill, Integer profit) {
        User driver = wayBill.getInvoice().getDriver();
        Payment payment = new Payment();
        double SALARY_MULTIPLIER = 0.01;
        payment.setPayment(profit * SALARY_MULTIPLIER);
        payment.setDate(new Date());
        payment.setUser(driver);
        paymentRepository.save(payment);
    }
}
