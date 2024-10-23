package com.saadmeddiche.creditmanagement.services;

import com.saadmeddiche.creditmanagement.dtos.PersonCreateRequest;
import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.repositories.PersonRepository;
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

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
