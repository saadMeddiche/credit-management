package com.saadmeddiche.creditmanagement.repositories;

import com.saadmeddiche.creditmanagement.entities.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Override
    @EntityGraph(attributePaths = {"credits", "phoneNumbers"})
    List<Person> findAll();


}
