package com.saadmeddiche.creditmanagement.controllers;

import com.saadmeddiche.creditmanagement.dtos.PhoneNumberCreateRequest;
import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.exeptions.RecordNotFound;
import com.saadmeddiche.creditmanagement.global_constants.PhoneNumberAPIs;
import com.saadmeddiche.creditmanagement.services.PersonService;
import com.saadmeddiche.creditmanagement.services.PhoneNumberService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    private final PersonService personService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(PhoneNumberAPIs.PHONE_NUMBERS_CREATE)
    public void createPhoneNumber(@PathVariable Long personId , @RequestBody List<PhoneNumberCreateRequest> phoneNumberCreateRequests) {

        Person person = personService.getPersonById(personId)
                .orElseThrow(() -> new RecordNotFound(String.format("Person with id %d not found", personId)));

        phoneNumberService.createPhoneNumber(person, phoneNumberCreateRequests);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(PhoneNumberAPIs.PHONE_NUMBERS_DELETE)
    public void deletePhoneNumber(@RequestParam("phoneNumberIds") @NotEmpty List<@NotNull Long> phoneNumberIds) {
        phoneNumberService.deletePhoneNumber(phoneNumberIds);
    }
}
