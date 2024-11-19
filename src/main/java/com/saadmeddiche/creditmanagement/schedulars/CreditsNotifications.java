package com.saadmeddiche.creditmanagement.schedulars;

import com.saadmeddiche.creditmanagement.repositories.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CreditsNotifications {

    private final CreditRepository creditRepository;

    @Scheduled(cron = "* * * * * *")
    public void test1() {
        System.out.println("test1");

        // Sleep for 5 seconds
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "* * * * * *")
    public void test2() {
        System.out.println("test2");


    }
}
