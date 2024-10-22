package com.saadmeddiche.creditmanagement.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

import java.util.logging.Logger;

@Profile("local")
public abstract class Seeder implements CommandLineRunner {

    private final String SEED_NAME = this.getClass().getName().toLowerCase();
    private final Logger logger = Logger.getLogger(SEED_NAME);

    @Override
    final public void run(String... args) throws Exception {
        logger.info(String.format("Seeding %s ...", SEED_NAME));
        seeding(); // The process of seeding is delegated to the concrete class
        logger.info(String.format("Successfully seeded %s", SEED_NAME));
    }

    protected abstract void seeding();
}
