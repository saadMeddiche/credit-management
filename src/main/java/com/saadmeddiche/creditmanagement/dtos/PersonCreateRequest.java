package com.saadmeddiche.creditmanagement.dtos;

import com.saadmeddiche.creditmanagement.annotations.NotExist;
import com.saadmeddiche.creditmanagement.entities.Person;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@NotExist(entity = Person.class , formFieldNames = {"email"})
public record PersonCreateRequest(
        @NotBlank @NotEmpty String firstName,
        @NotBlank @NotEmpty String lastName,
        @NotBlank @NotEmpty @Email String email,
        @NotBlank @NotEmpty String job,
        String description
) {
}
