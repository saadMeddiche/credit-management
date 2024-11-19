package com.saadmeddiche.creditmanagement.repositories;

import com.saadmeddiche.creditmanagement.entities.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {}
