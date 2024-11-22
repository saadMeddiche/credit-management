package com.saadmeddiche.creditmanagement.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public abstract class ExportService<T> {

    private final Workbook workbook;

    private final Sheet sheet;

    private int rowIndex;

    private final ByteArrayOutputStream outputStream;

    private final Logger logger;

    private final String[] fieldNames;

    public abstract String sheetName();

    public abstract Class<T> clazz();

    protected ExportService() {
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet(sheetName());
        this.outputStream = new ByteArrayOutputStream();
        this.logger = Logger.getLogger(this.getClass().getName());
        this.fieldNames = extractFieldNames();
        this.rowIndex = 0;
    }

    protected byte[] exportToExcelBytes(List<T> dataCollection) {

        createHeaderRow();

        populateDataRows(dataCollection);

        writeToStream();

        close();

        return outputStream.toByteArray();
    }

    private void createHeaderRow() {
        Row headerRow = sheet.createRow(rowIndex++);

        for (int i = 0; i < fieldNames.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(fieldNames[i]);
        }
    }

    private void populateDataRows(List<T> dataCollection) {

        for (T data : dataCollection) {

            Row row = sheet.createRow(rowIndex++);

            for (int j = 0; j < fieldNames.length; j++) {
                try {
                    Field field = clazz().getDeclaredField(fieldNames[j]);
                    field.setAccessible(true);
                    Object value = field.get(data);
                    row.createCell(j).setCellValue(value == null ? "" : value.toString());
                } catch (Exception e) {
                    logger.warning("Error occurred while populating data rows");
                    logger.warning(e.getMessage());
                }
            }
        }
    }

    private String[] extractFieldNames(){
        return Stream.of(clazz().getDeclaredFields())
                .map(Field::getName)
                .toArray(String[]::new);
    }

    private void writeToStream() {
        try {
            workbook.write(outputStream);
        } catch (Exception e) {
            logger.warning("Error occurred while writing to output stream");
            logger.warning(e.getMessage());
        }
    }

    private void close() {
        try {
            workbook.close();
        } catch (Exception e) {
            logger.warning("Error occurred while closing workbook");
            logger.warning(e.getMessage());
        }

        try {
            outputStream.close();
        } catch (Exception e) {
            logger.warning("Error occurred while closing output stream");
            logger.warning(e.getMessage());
        }
    }
}
