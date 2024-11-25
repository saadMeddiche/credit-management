package com.saadmeddiche.creditmanagement.services;

import com.saadmeddiche.creditmanagement.dtos.CreditCreateRequest;
import com.saadmeddiche.creditmanagement.entities.Credit;
import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.repositories.CreditRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service @Transactional
@RequiredArgsConstructor @Validated
public class CreditService {

    private final CreditRepository creditRepository;

    private final ModelMapper modelMapper;

    public void createCredit(Person person, @Valid CreditCreateRequest creditCreateRequest){

        Credit credit = modelMapper.map(creditCreateRequest, Credit.class);

        credit.setPerson(person);

        creditRepository.save(credit);

    }

    public Map<Person, List<Credit>> findCreditsThatReachedTheirPaymentDate() {

        List<Object[]> data = creditRepository.findCreditsThatReachedTheirPaymentDate();

        return data.stream().collect(
                Collectors.groupingBy(
                        objects -> (Person) objects[0],
                        Collectors.mapping(objects -> (Credit) objects[1], Collectors.toList())
                )
        );
    }
}
