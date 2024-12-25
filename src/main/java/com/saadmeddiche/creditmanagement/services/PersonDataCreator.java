package com.saadmeddiche.creditmanagement.services;

import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.repositories.PersonRepository;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service @Slf4j
@Validated @RequiredArgsConstructor
public class PersonDataCreator {

    private final PersonRepository personRepository;

    public void createPersonData(@NotEmpty(message = "Path must not be null nor empty.") String path) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {

            log.debug("[1*] Reading raw data from the file");
            List<Person> rawPersons = extractPersons(lines);
            log.debug("[1-] Data extracted successfully");

            log.debug("[2*] Extracting raw emails from the data");
            Map<String,Person> rawEmails = extractEmails(rawPersons);
            log.debug("[2-] Emails extracted successfully");

            log.debug("[3*] Fetching persons from the database using the raw emails");
            List<Person> persons = pullPersons(rawEmails.keySet());
            log.debug("[3-] Persons fetched successfully");

            log.debug("[4*] Updating persons");
            Map<String,Person> emailsOfNotExitedPersons =  updatePersons(persons, rawEmails);
            log.debug("[4-] Persons updated successfully");

            log.debug("[5*] Saving the persons that are not in the database");
            personRepository.saveAll(emailsOfNotExitedPersons.values());
            log.debug("[5-] Persons saved successfully");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Person> pullPersons(Set<String> emails) {

        List<Person> persons = new ArrayList<>();

        int batchSize = 65535;

        for (int i = 0; i < emails.size(); i += batchSize) {
            List<String> batchEmails = emails.stream().skip(i).limit(batchSize).toList();
            persons.addAll(personRepository.findByEmailIn(new HashSet<>(batchEmails)));
        }

        return persons;

    }

    // Returns what is left of the rawEmails after removing the emails of the persons
    private Map<String,Person> updatePersons(List<Person> persons, Map<String,Person> rawEmails) {

        Set<String> updatedEmails = new HashSet<>();

        for (Person person : persons) {

            Person rawPerson = rawEmails.get(person.getEmail());

            person.setFirstName(rawPerson.getFirstName());
            person.setLastName(rawPerson.getLastName());
            person.setJob(rawPerson.getJob());
            person.setDescription(rawPerson.getDescription());

            updatedEmails.add(person.getEmail());
        }

        personRepository.saveAll(persons);

        // Remove the emails of the persons from the rawEmails
        rawEmails.keySet().removeAll(updatedEmails);

        return rawEmails;
    }

    private List<Person> extractPersons(Stream<String> lines) {
        return lines.map(this::extractPerson).collect(Collectors.toList());
    }

    private Person extractPerson(String line) {

        String[] data = line.split(";");

        return Person.builder()
                .firstName(data[0])
                .lastName(data[1])
                .email(data[2])
                .job(data[3])
                .description(data[4])
                .build();
    }

    private Map<String,Person> extractEmails(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.toMap(this::extractEmail, person -> person, (existing, replacement) -> replacement));
    }

    private String extractEmail(Person person) {
        return person.getEmail();
    }
}
