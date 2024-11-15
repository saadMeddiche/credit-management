package com.saadmeddiche.creditmanagement.seeders;

import com.github.javafaker.Faker;
import com.saadmeddiche.creditmanagement.entities.FrequentFlyer;
import com.saadmeddiche.creditmanagement.entities.Passanger;
import com.saadmeddiche.creditmanagement.repositories.PassangerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor @Configuration
public class PassangerSeeder extends Seeder {

    private final PassangerRepository passangerRepository;

    private final Faker faker = new Faker();

    private final List<String> levels = List.of("Blue", "Silver", "Gold", "Platinum");

    private final Random random = new Random();

    @Override
    public void seeding() {

        List<Passanger> passangers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

        }

    }

    private Passanger buildPassanger() {
        return Passanger.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .ffnNumber(buildFrequentFlyer())
                .pnr(RandomStringUtils.randomAlphabetic(6).toUpperCase())
                .build();
    }

    private Passanger buildPassanger(FrequentFlyer ffn) {
        return Passanger.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .ffnNumber(ffn)
                .build();
    }

    private FrequentFlyer buildFrequentFlyer() {
        return FrequentFlyer.builder()
                .ffnNo(RandomStringUtils.randomNumeric(9))
                .tierLevel(levels.get(random.nextInt(levels.size())))
                .miles(null)
                .build();
    }

}
