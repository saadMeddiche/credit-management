package com.saadmeddiche.creditmanagement.services;

import com.saadmeddiche.creditmanagement.entities.Credit;
import org.springframework.stereotype.Component;

@Component
public class CreditExportService extends SimpleExportService<Credit> {

    @Override
    String getFileName() {
        return "credits.xlsx";
    }

    @Override
    String getApplicationName() {
        return "Credit Management";
    }

    @Override
    String getSheetName() {
        return "Credits";
    }

    @Override
    String getApplicationVersion() {
        return "1.0";
    }
}
