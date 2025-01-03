package com.saadmeddiche.creditmanagement.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public abstract class ExportService<T> {

    private final Workbook workbook;

    private final Sheet sheet;

    private int rowIndex;

    private final ByteArrayOutputStream outputStream;

    private final String[] fieldNames;

    public abstract String sheetName();

    public abstract Class<T> clazz();

    protected ExportService() {
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet(sheetName());
        this.outputStream = new ByteArrayOutputStream();
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
                    log.warn("Error occurred while populating data row number {}", rowIndex);
                    log.trace("Exception :: {}", e.getMessage());
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
            log.warn("Error occurred while writing to output stream");
            log.trace("Exception :: {}",e.getMessage());
        }
    }

    private void close() {
        try {
            workbook.close();
        } catch (Exception e) {
            log.warn("Error occurred while closing workbook");
            log.trace("Exception :: {}",e.getMessage());
        }

        try {
            outputStream.close();
        } catch (Exception e) {
            log.warn("Error occurred while closing output stream");
            log.trace("Exception :: {}",e.getMessage());
        }
    }
}
