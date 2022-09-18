package by.singularity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CargoTruckingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CargoTruckingApplication.class);
    }
}
