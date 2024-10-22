package com.saadmeddiche.creditmanagement.controller;

import com.saadmeddiche.creditmanagement.dto.PersonCreateRequest;
import com.saadmeddiche.creditmanagement.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
