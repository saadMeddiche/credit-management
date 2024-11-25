package com.saadmeddiche.creditmanagement.seeders;

import com.github.javafaker.Faker;
import com.saadmeddiche.creditmanagement.conditions.PersonSeederEnabledCondition;
import com.saadmeddiche.creditmanagement.entities.Credit;
import com.saadmeddiche.creditmanagement.entities.Person;
import com.saadmeddiche.creditmanagement.entities.PhoneNumber;
import com.saadmeddiche.creditmanagement.enums.CreditType;
import com.saadmeddiche.creditmanagement.enums.Currency;
import com.saadmeddiche.creditmanagement.properties.PersonSeederProperties;
import com.saadmeddiche.creditmanagement.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Order(1)
@Profile("local")
@RequiredArgsConstructor @Configuration
@Conditional(PersonSeederEnabledCondition.class)
public class PersonSeeder extends Seeder {

    private final PersonRepository personRepository;

    private final PersonSeederProperties properties;

    private final Faker faker = new Faker();


    @Override
    public void seeding() {

        Date start = new Date();
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < properties.getPersonCount(); i++) {
            persons.add(buildPerson());
        }
        personRepository.saveAll(persons);
        Date end = new Date();
        System.out.println("PersonSeeder: " + properties.getPersonCount() + " persons have been seeded in " + (end.getTime() - start.getTime()) + " ms");
    }

    private Person buildPerson() {
        Person person = Person.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .job(faker.company().profession())
                .description(faker.lorem().paragraph())
                .build();

        person.setPhoneNumbers(generatePhoneNumbers(person));

        person.setCredits(generateCredits(person));

        return person;
    }

    private Set<PhoneNumber> generatePhoneNumbers(Person person) {
        Set<PhoneNumber> phoneNumbers = new HashSet<>();
        for (int i = 0; i < properties.getPhoneNumberPerPerson(); i++) {
            phoneNumbers.add(buildPhoneNumber(person));
        }
        return phoneNumbers;
    }

    private Set<Credit> generateCredits(Person person) {
        Set<Credit> credits = new HashSet<>();
        for (int i = 0; i < properties.getCreditPerPerson(); i++) {
            credits.add(buildCredit(person));
        }
        return credits;
    }

    private PhoneNumber buildPhoneNumber(Person person) {
        return PhoneNumber.builder()
                .number(faker.phoneNumber().cellPhone())
                .countryCode(faker.phoneNumber().extension())
                .person(person)
                .build();
    }

    private Credit buildCredit(Person person) {
        return Credit.builder()
                .amount(faker.number().randomDouble(2, 100, 10000))
                .currency(Currency.values()[faker.number().numberBetween(0, Currency.values().length)])
                .creditType(CreditType.values()[faker.number().numberBetween(0, CreditType.values().length)])
                .reason(faker.lorem().sentence())
                .grantDate(faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .paymentDate(faker.date().future(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .person(person)
                .build();
    }

}
