package com.saadmeddiche.creditmanagement.services;

import com.saadmeddiche.creditmanagement.dtos.PersonCreateRequest;
import com.saadmeddiche.creditmanagement.dtos.PersonUpdateRequest;
import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.entities.embeddables.PhoneNumber;
import com.saadmeddiche.creditmanagement.repositories.PersonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor @Validated
public class PersonService {

    private final PersonRepository personRepository;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public void createPerson(@Valid PersonCreateRequest personCreateRequest) {

        Person person = modelMapper.map(personCreateRequest , Person.class);

        personRepository.save(person);
    }

    public void updatePerson(Person person , @Valid PersonUpdateRequest personUpdateRequest) {

        modelMapper.map(personUpdateRequest , person);

        personUpdateRequest.phoneNumberRequests().forEach(phoneNumberRequest -> {
            if(phoneNumberRequest.add()){
                person.getPhoneNumbers().add(modelMapper.map(phoneNumberRequest , PhoneNumber.class));
            } else {
                person.getPhoneNumbers().removeIf(phoneNumber -> phoneNumber.getNumber().equals(phoneNumberRequest.number()) && phoneNumber.getCountryCode().equals(phoneNumberRequest.countryCode()));
            }
        });

    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

}
