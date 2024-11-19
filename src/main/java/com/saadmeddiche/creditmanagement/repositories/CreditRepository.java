package com.saadmeddiche.creditmanagement.repositories;

import com.saadmeddiche.creditmanagement.entities.Credit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {

    @EntityGraph(value = "Credit.withPerson")
    @Query("SELECT c FROM Credit c WHERE c.paymentDate <= CURRENT_TIMESTAMP")
    List<Credit> findCreditsThatReachedTheirPaymentDate();
}
