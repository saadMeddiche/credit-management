package com.saadmeddiche.creditmanagement.controllers;

import com.saadmeddiche.creditmanagement.dtos.CreditCreateRequest;
import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.exeptions.RecordNotFound;
import com.saadmeddiche.creditmanagement.global_constants.CreditAPIs;
import com.saadmeddiche.creditmanagement.services.CreditService;
import com.saadmeddiche.creditmanagement.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    private final PersonService personService;

    @PostMapping(CreditAPIs.CREATE_CREDIT) @ResponseStatus(HttpStatus.CREATED)
    public void createCredit(@PathVariable Long personId , @RequestBody CreditCreateRequest creditCreateRequest){

        Person person = personService.getPersonById(personId)
                .orElseThrow(() -> new RecordNotFound(String.format("Person with id %d not found", personId)));

        creditService.createCredit(person, creditCreateRequest);

    }
}
