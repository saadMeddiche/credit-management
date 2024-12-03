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

            List<Person> persons = extractPersons(stream);

            Map<Boolean, List<Person>> partitioned = persons.stream()
                    .collect(Collectors.partitioningBy(person -> personRepository.existsByEmail(person.getEmail())));

            List<Person> newPersons = partitioned.get(Boolean.FALSE);

            if (!newPersons.isEmpty()) {
                personRepository.saveAll(newPersons);
                log.info("New persons added to the database");
            } else {
                log.info("No new persons to add to the database");
            }

            List<Person> existingPersons = partitioned.get(Boolean.TRUE);

            if (!existingPersons.isEmpty()) {
                for (Person existingPerson : existingPersons) {

                    Person person = personRepository.findByEmail(existingPerson.getEmail())
                            .orElseThrow(() -> new RuntimeException("Person not found in the database, but it should be there"));

                    person.setFirstName(existingPerson.getFirstName());
                    person.setLastName(existingPerson.getLastName());
                    person.setJob(existingPerson.getJob());
                    person.setDescription(existingPerson.getDescription());

                    personRepository.save(person);

                }
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
