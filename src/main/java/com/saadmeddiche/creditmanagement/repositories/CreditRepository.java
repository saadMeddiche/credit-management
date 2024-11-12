package com.saadmeddiche.creditmanagement.repositories;

import com.saadmeddiche.creditmanagement.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> { }
