package com.saadmeddiche.creditmanagement.controllers;

import com.saadmeddiche.creditmanagement.dtos.PersonCreateRequest;
import com.saadmeddiche.creditmanagement.dtos.PersonUpdateRequest;
import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.services.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Void> createPerson(@Valid @RequestBody PersonCreateRequest personCreateRequest) {
        personService.createPerson(personCreateRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable @NotNull Long id,@Valid @RequestBody PersonUpdateRequest personUpdateRequest) {

        Optional<Person> personOptional = personService.getPersonById(id);

        if(personOptional.isEmpty()) return ResponseEntity.notFound().build();

        personService.updatePerson(personOptional.get(), personUpdateRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable @NotNull Long id) {
        personService.deletePerson(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable @NotNull Long id) {
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
