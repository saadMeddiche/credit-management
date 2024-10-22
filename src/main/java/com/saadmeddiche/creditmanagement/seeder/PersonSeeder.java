package com.saadmeddiche.creditmanagement.seeder;

import com.github.javafaker.Faker;
import com.saadmeddiche.creditmanagement.condition.PersonSeederEnabledCondition;
import com.saadmeddiche.creditmanagement.entity.Person;
import com.saadmeddiche.creditmanagement.entity.embeddable.PhoneNumber;
import com.saadmeddiche.creditmanagement.property.PersonSeederProperties;
import com.saadmeddiche.creditmanagement.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("local")
@RequiredArgsConstructor @Configuration
@Conditional(PersonSeederEnabledCondition.class)
public class PersonSeeder extends Seeder {

    private final PersonRepository personRepository;

    private final PersonSeederProperties properties;

    @Override
    public void seeding() {
        for (int i = 0; i < properties.getPersonCount(); i++) {
            personRepository.save(buildPerson());
        }
    }

    private Person buildPerson() {
        Faker faker = new Faker();
        return Person.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .job(faker.company().profession())
                .description(faker.lorem().paragraph())
                .phoneNumbers(generatePhoneNumbers())
                .build();
    }

    private List<PhoneNumber> generatePhoneNumbers() {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        for (int i = 0; i < properties.getPhoneNumberPerPerson(); i++) {
            phoneNumbers.add(buildPhoneNumber());
        }
        return phoneNumbers;
    }

    private PhoneNumber buildPhoneNumber() {
        Faker faker = new Faker();
        return PhoneNumber.builder()
                .number(faker.phoneNumber().cellPhone())
                .countryCode(faker.phoneNumber().extension())
                .build();
    }

}
