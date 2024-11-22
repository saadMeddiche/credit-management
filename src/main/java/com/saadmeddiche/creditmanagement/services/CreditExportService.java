package com.saadmeddiche.creditmanagement.services;

import com.saadmeddiche.creditmanagement.entities.Credit;

import java.io.File;
import java.io.FileOutputStream;
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

    public File export(List<Credit> credits) throws Exception {
        byte[] excelBytes = exportToExcelBytes(credits);
        File file = new File("credits.xlsx");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(excelBytes);
        }
        return file;
    }
}
