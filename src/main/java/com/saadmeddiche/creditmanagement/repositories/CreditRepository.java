package com.saadmeddiche.creditmanagement.repositories;

import com.saadmeddiche.creditmanagement.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {

    @Query("SELECT c FROM Credit c join fetch c.person p join fetch p.phoneNumbers WHERE c.paymentDate <= CURRENT_TIMESTAMP")
    List<Credit> findCreditsThatReachedTheirPaymentDate();
}
