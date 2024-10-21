package com.saadmeddiche.creditmanagement.repositories;

import com.saadmeddiche.creditmanagement.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
