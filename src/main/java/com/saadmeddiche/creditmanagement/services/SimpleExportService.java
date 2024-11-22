package com.saadmeddiche.creditmanagement.services;

import org.dhatim.fastexcel.Position;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

public abstract class SimpleExportService<T> {

    abstract String getFileName();

    abstract String getApplicationName();

    abstract String getSheetName();

    abstract String getApplicationVersion();

    public final byte[] export(List<T> data, Class<T> clazz) throws IOException, IllegalAccessException {

        OutputStream outputStream = new FileOutputStream(getFileName());

        Workbook workbook = new Workbook(outputStream, getApplicationName(), getApplicationVersion());

        Worksheet worksheet = workbook.newWorksheet(getSheetName());

        createHeaders(worksheet , clazz);

        createRows(worksheet , data , clazz);

        workbook.finish();

        outputStream.close();
        workbook.close();

        return outputStream.toString().getBytes();

    }

    private void createHeaders(Worksheet worksheet , Class<T> clazz){
        Stream.of(clazz.getDeclaredFields())
                .map(Field::getName)
                .forEach(header -> worksheet.header(header, Position.CENTER));
    }

    private void createRows(Worksheet worksheet , List<T> data , Class<T> clazz) throws IllegalAccessException {
        List<Field> fields = List.of(clazz.getDeclaredFields());

        for (int i = 0; i < data.size(); i++) {
            T object = data.get(i);
            for (int j = 0; j < fields.size(); j++) {
                Field field = fields.get(j);
                field.setAccessible(true);
                worksheet.value(i + 1, j , field.get(object).toString());
            }
        }
    }
}
