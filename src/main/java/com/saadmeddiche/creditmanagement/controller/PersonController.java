package com.saadmeddiche.creditmanagement.controller;

import com.saadmeddiche.creditmanagement.dto.PersonCreateRequest;
import com.saadmeddiche.creditmanagement.entity.Person;
import com.saadmeddiche.creditmanagement.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody PersonCreateRequest personCreateRequest) {
        personService.createPerson(personCreateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

}
