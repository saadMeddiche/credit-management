package com.saadmeddiche.creditmanagement.schedulars;

import com.saadmeddiche.creditmanagement.entities.Credit;
import com.saadmeddiche.creditmanagement.repositories.CreditRepository;
import com.saadmeddiche.creditmanagement.services.CreditExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreditsExportation implements Scheduler {

    private final CreditRepository creditRepository;

    private final CreditExportService creditExportService;

    @Override @Scheduled(cron = "0 0/10 * * * *")
    public void task() throws IOException, IllegalAccessException {
        log.info("Exporting credits");
        List<Credit> credits = creditRepository.findCreditsThatReachedTheirPaymentDate();

        byte[] bytes = creditExportService.export(credits, Credit.class);

    }

}
