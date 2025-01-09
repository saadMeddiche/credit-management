package com.saadmeddiche.creditmanagement.runners;

import com.saadmeddiche.creditmanagement.properties.CustomStaticResourceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@Component @Slf4j
@RequiredArgsConstructor
public class CustomStaticResourceRunner implements CommandLineRunner {

    private final CustomStaticResourceProperties properties;

    @Override
    public void run(String... args) throws IOException {

        log.info("Creating directory: {}", properties.getPath());
        Files.createDirectories(properties.getPath());
        log.info("Directory created successfully");

    }

}
