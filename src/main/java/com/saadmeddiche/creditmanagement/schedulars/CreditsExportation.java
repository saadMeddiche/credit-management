package com.saadmeddiche.creditmanagement.schedulars;

import com.saadmeddiche.creditmanagement.entities.Credit;
import com.saadmeddiche.creditmanagement.repositories.CreditRepository;
import com.saadmeddiche.creditmanagement.services.CreditExportService;
import com.saadmeddiche.creditmanagement.services.SimpleMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreditsExportation implements Scheduler {

    private final CreditRepository creditRepository;

    private final SimpleMailService simpleMailService;

    @Override @Scheduled(cron = "0/10 * * * * *")
    public void task() throws Exception {
        log.info("Exporting credits");
        List<Credit> credits = creditRepository.findCreditsThatReachedTheirPaymentDate();

        File excel = new CreditExportService().export(credits);

        credits.forEach(credit -> {
            try {
                simpleMailService.sendMail(credit.getPerson().getEmail(), "Credit Payment Reminder", "Please pay your credit", Collections.singletonList(excel));
            } catch (Exception e) {
                log.error("Error while sending email", e);
            }
        });

        excel.deleteOnExit();
    }

}
