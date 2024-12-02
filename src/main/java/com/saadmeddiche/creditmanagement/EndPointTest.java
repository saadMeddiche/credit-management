package com.saadmeddiche.creditmanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class EndPointTest {

    private static final String URL = "http://localhost:8080/persons/generate-file?path=persons.txt&knownPersonCount=20&unknownPersonCount=80";

    private static final int NUM_THREADS = 50;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(() -> {
                RestTemplate restTemplate = new RestTemplate();

                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
                    log.info("Response: {}", response.getBody());
                } catch (Exception e) {
                    log.error("Error: {}", e.getMessage());
                }

            });
        }

        executorService.shutdown();
    }
}
