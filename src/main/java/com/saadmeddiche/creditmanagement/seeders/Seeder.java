package com.saadmeddiche.creditmanagement.seeders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("local")
public abstract class Seeder implements CommandLineRunner {

    private final String SEED_NAME = this.getClass().getSimpleName().toLowerCase();

    @Override
    final public void run(String... args) throws Exception {
        log.info("Seeding {} ...", SEED_NAME);
        seeding(); // The process of seeding is delegated to the concrete class
        log.info("Successfully seeded {}", SEED_NAME);
    }

    protected abstract void seeding();
}
