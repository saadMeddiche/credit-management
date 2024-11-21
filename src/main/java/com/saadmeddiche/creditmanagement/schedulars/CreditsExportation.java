package com.saadmeddiche.creditmanagement.schedulars;

import com.saadmeddiche.creditmanagement.entities.Credit;
import com.saadmeddiche.creditmanagement.repositories.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreditsExportation implements Scheduler {

    private final CreditRepository creditRepository;

    @Override @Scheduled(cron = "0 0/10 * * * *")
    public void task() {
        log.info("Exporting credits");
        List<Credit> credits = creditRepository.findCreditsThatReachedTheirPaymentDate();

    }

}
