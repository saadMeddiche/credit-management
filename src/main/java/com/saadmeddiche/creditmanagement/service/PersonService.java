package com.saadmeddiche.creditmanagement.service;

import com.saadmeddiche.creditmanagement.dto.PersonCreateRequest;
import com.saadmeddiche.creditmanagement.entity.Person;
import com.saadmeddiche.creditmanagement.repository.PersonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service @Transactional
@RequiredArgsConstructor @Validated
public class PersonService {

    private final PersonRepository personRepository;

    private final ModelMapper modelMapper;

    public void createPerson(@Valid PersonCreateRequest personCreateRequest) {

        Person person = modelMapper.map(personCreateRequest , Person.class);

        personRepository.save(person);
    }

    @Transactional(readOnly = true)
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
