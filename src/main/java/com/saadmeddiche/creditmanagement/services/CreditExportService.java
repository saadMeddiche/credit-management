package com.saadmeddiche.creditmanagement.services;

import com.saadmeddiche.creditmanagement.entities.Credit;

import java.util.List;


public class CreditExportService extends ExportService<Credit> {

    @Override
    public String sheetName() {
        return "Credits";
    }

    @Override
    public Class<Credit> clazz() {
        return Credit.class;
    }

    public byte[] export(List<Credit> credits) {
        return exportToExcelBytes(credits);
    }
}
