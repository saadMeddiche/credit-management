package com.saadmeddiche.creditmanagement.repository;

import com.saadmeddiche.creditmanagement.entity.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Override
    @EntityGraph(attributePaths = {"phoneNumbers"})
    List<Person> findAll();
}
