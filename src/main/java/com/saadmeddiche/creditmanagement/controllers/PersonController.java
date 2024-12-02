package com.saadmeddiche.creditmanagement.controllers;

import com.saadmeddiche.creditmanagement.dtos.PersonCreateRequest;
import com.saadmeddiche.creditmanagement.dtos.PersonUpdateRequest;
import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.exeptions.RecordNotFound;
import com.saadmeddiche.creditmanagement.global_constants.CacheNames;
import com.saadmeddiche.creditmanagement.global_constants.PersonAPIs;
import com.saadmeddiche.creditmanagement.services.PersonFileCreator;
import com.saadmeddiche.creditmanagement.services.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    private final PersonFileCreator personFileCreator;

    @GetMapping(PersonAPIs.PERSONS)
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping(PersonAPIs.PERSONS_GET) @Cacheable(value = CacheNames.PERSONS, key = "#id")
    public Person getPersonById(@PathVariable @NotNull Long id) {
        return personService.getPersonById(id)
                .orElseThrow(() -> new RecordNotFound(String.format("Person with id %d not found", id)));
    }

    @PostMapping(PersonAPIs.PERSONS_CREATE) @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@Valid @RequestBody PersonCreateRequest personCreateRequest) {

        personService.createPerson(personCreateRequest);

    }

    @PutMapping(PersonAPIs.PERSONS_UPDATE) @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePerson(@PathVariable @NotNull Long id,@Valid @RequestBody PersonUpdateRequest personUpdateRequest) {

        Person person = personService.getPersonById(id)
                .orElseThrow(() -> new RecordNotFound(String.format("Person with id %d not found", id)));

        personService.updatePerson(person, personUpdateRequest);

    }

    @DeleteMapping(PersonAPIs.PERSONS_DELETE) @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable @NotNull Long id) {

        personService.deletePerson(id);

    }

    @GetMapping(PersonAPIs.PERSONS_GENERATE_FILE) @ResponseStatus(HttpStatus.NO_CONTENT)
    public void generatePersonFile(@RequestParam String path, @RequestParam Long knownPersonCount, @RequestParam Long unknownPersonCount) {

        personFileCreator.generatePersonFile(path, knownPersonCount, unknownPersonCount);

    }

}
