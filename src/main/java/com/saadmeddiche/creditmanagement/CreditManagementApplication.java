package com.saadmeddiche.creditmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CreditManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditManagementApplication.class, args);
	}

}
