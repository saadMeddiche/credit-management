package com.saadmeddiche.creditmanagement.schedulars;

import com.saadmeddiche.creditmanagement.entities.Credit;
import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.services.CreditExportService;
import com.saadmeddiche.creditmanagement.services.CreditService;
import com.saadmeddiche.creditmanagement.services.SimpleMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreditsExportation implements Scheduler {

    private final CreditService creditService;

    private final SimpleMailService simpleMailService;

    @Override @Scheduled(cron = "0/10 * * * * *")
    public void task() {
        log.info("===> Exporting credits <===");
        Map<Person, List<Credit>> creditMap = creditService.findCreditsThatReachedTheirPaymentDate();

        for (Map.Entry<Person, List<Credit>> entry : creditMap.entrySet()) {

            Person person = entry.getKey();

            List<Credit> credits = entry.getValue();

            export(credits).ifPresent(file -> {
                sendMail(person, file);
                log.info("Credits exported and mail sent to {}" , person.getEmail());
            });

        }

        log.info("===> Credits exported <===");
    }

    private Optional<File> export(List<Credit> credits) {
        try {
            return Optional.of(new CreditExportService().export(credits));
        } catch (Exception e) {
            log.error("An error occurred while exporting credits", e);
            return Optional.empty();
        }
    }

    private void sendMail(Person person, File file) {
        try {
            simpleMailService.sendMail(person.getEmail(), "Credits", "Please find attached the credits that reached their payment date", Collections.singletonList(file));
        } catch (Exception e) {
            log.error("An error occurred while sending mail to {}" , person.getEmail(), e);
        }
    }

}
