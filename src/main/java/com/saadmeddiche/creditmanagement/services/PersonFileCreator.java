package com.saadmeddiche.creditmanagement.services;

import com.github.javafaker.Faker;
import com.saadmeddiche.creditmanagement.repositories.PersonRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service @Slf4j
@Validated @RequiredArgsConstructor
public class PersonFileCreator {

    private final List<String> currentPaths = Collections.synchronizedList(new ArrayList<>());

    private final PersonRepository personRepository;

    private final Faker customFaker;

    private static final String SEPARATOR = ";";

    public void generatePersonFile(@NotEmpty(message = "Path must not be null nor empty.") String path,
                                   @PositiveOrZero(message = "KnownPersonCount must not be negative") Long knownPersonCount,
                                   @PositiveOrZero(message = "UnknownPersonCount must not be negative") Long unknownPersonCount) {

        if (currentPaths.contains(path))
            throw new IllegalArgumentException("This path is already in use");

        File file = createFile(path); log.info("File created successfully at path: {}", file.getAbsolutePath());

        writeToFile(file, knownPersonCount, unknownPersonCount); log.info("Data written successfully to the file");

        currentPaths.remove(path);
    }

    private File createFile(String path) {

        File file = new File(path);

        try {
            boolean result =  file.createNewFile();

            if (result) {
                currentPaths.add(path);
            } else {
                log.error("file.createNewFile() returned false");
                throw new IllegalArgumentException("File already exists");
            }

        } catch (Exception e) {
            log.error("Error while creating the file", e);
            throw new RuntimeException("Something went wrong while creating the file");
        }

        return file;
    }

    private void writeToFile(File file, Long knownPersonCount, Long unknownPersonCount) {

        List<String> knownPersons = buildKnowPersonsText(knownPersonCount);

        List<String> unknownPersons = buildUnknownPersonsText(unknownPersonCount);

        List<String> allPersons = shufflePersons(knownPersons, unknownPersons);

        String data = String.join(System.lineSeparator(), allPersons);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> shufflePersons(List<String> knownPersons, List<String> unknownPersons) {

        List<String> allPersons = new ArrayList<>(knownPersons);

        allPersons.addAll(unknownPersons);

        Collections.shuffle(allPersons);

        return allPersons;
    }

    private List<String> buildKnowPersonsText(Long knownPersonCount){
        List<String> emails = personRepository.findAllEmailsWithLimit(knownPersonCount);
        return buildPersons(emails);
    }

    private List<String> buildUnknownPersonsText(Long unknownPersonCount){
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < unknownPersonCount; i++) {
            emails.add(customFaker.internet().emailAddress());
        }
        return buildPersons(emails);
    }

    private List<String> buildPersons(List<String> emails){
        List<String> persons = new ArrayList<>();

        for (String email : emails) {
            persons.add(buildPerson(email));
        }

        return persons;
    }

    private String buildPerson(String email) {
        StringBuilder builder = new StringBuilder();

        builder.append(customFaker.name().firstName())
                .append(SEPARATOR)
                .append(customFaker.name().lastName())
                .append(SEPARATOR)
                .append(email)
                .append(SEPARATOR)
                .append(customFaker.company().profession())
                .append(SEPARATOR)
                .append(customFaker.lorem().paragraph());

        return builder.toString();
    }
}
