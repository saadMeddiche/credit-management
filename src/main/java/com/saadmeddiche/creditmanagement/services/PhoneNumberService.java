package com.saadmeddiche.creditmanagement.services;

import com.saadmeddiche.creditmanagement.dtos.PhoneNumberCreateRequest;
import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.entities.PhoneNumber;
import com.saadmeddiche.creditmanagement.repositories.PhoneNumberRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service @Transactional
@RequiredArgsConstructor @Validated
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    private final ModelMapper modelMapper;

    public void deletePhoneNumber(@NotEmpty List<@NotNull Long> phoneNumberIds) {
        phoneNumberRepository.deleteAllById(phoneNumberIds);
    }

    public void createPhoneNumber(Person person , @Valid List<PhoneNumberCreateRequest> phoneNumberCreateRequests) {

        List<PhoneNumber> phoneNumbers = phoneNumberCreateRequests.stream()
                .map(phoneNumberCreateRequest -> modelMapper.map(phoneNumberCreateRequest, PhoneNumber.class))
                .peek(phoneNumber -> phoneNumber.setPerson(person))
                .toList();

        phoneNumberRepository.saveAll(phoneNumbers);

    }
}
