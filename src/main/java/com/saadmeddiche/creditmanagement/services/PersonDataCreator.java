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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service @Slf4j
@Validated @RequiredArgsConstructor
public class PersonDataCreator {

    private final PersonRepository personRepository;

    public void createPersonData(@NotEmpty(message = "Path must not be null nor empty.") String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {

            log.debug("Reading data from the file");
            List<Person> persons = extractPersons(stream);
            log.debug("Data extracted successfully");


            log.debug("Checking if persons already exist in the database");
            Map<Boolean, List<Person>> partitioned = persons.stream()
                    .collect(Collectors.partitioningBy(person -> personRepository.existsByEmail(person.getEmail())));
            log.debug("Partitioned the data successfully");

            log.debug("Saving newPersons ...");
            List<Person> newPersons = partitioned.get(Boolean.FALSE);

            if (!newPersons.isEmpty()) {

                Map<String,Person> emailPersons = new LinkedHashMap<>();

                for (Person newPerson : newPersons) {
                    emailPersons.put(newPerson.getEmail(), newPerson);
                }

                personRepository.saveAll(emailPersons.values());

                log.info("New persons added to the database");
            } else {
                log.info("No new persons to add to the database");
            }

            log.debug("Updating existingPersons ...");
            List<Person> existingPersons = partitioned.get(Boolean.TRUE);

            if (!existingPersons.isEmpty()) {

                log.debug("Getting existing persons from the database");
                List<Person> personList = personRepository.findByEmailIn(existingPersons.stream().map(Person::getEmail).toList());

                log.debug("Mapping existing persons to a map");
                Map<String,Person> emailPersons = new LinkedHashMap<>();

                for (Person existingPerson : existingPersons) {
                    emailPersons.put(existingPerson.getEmail(), existingPerson);
                }

                log.debug("Updating existing persons ...");
                for (Person person : personList) {
                    Person newPerson = emailPersons.get(person.getEmail());
                    person.setFirstName(newPerson.getFirstName());
                    person.setLastName(newPerson.getLastName());
                    person.setJob(newPerson.getJob());
                    person.setDescription(newPerson.getDescription());
                }

                personRepository.saveAll(personList);

                log.info("Persons updated in the database");
            } else {
                log.info("No persons already exist in the database");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Person> extractPersons(Stream<String> stream) {
        return stream.map(this::extractPerson).toList();
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
}
