package com.saadmeddiche.creditmanagement.repository;

import com.saadmeddiche.creditmanagement.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
