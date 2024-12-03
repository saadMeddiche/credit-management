package com.saadmeddiche.creditmanagement.repositories;

import com.saadmeddiche.creditmanagement.entities.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Override
    @EntityGraph(attributePaths = {"credits", "phoneNumbers"})
    List<Person> findAll();

    @Query("SELECT p.email FROM Person p ORDER BY p.id ASC LIMIT :limit")
    List<String> findAllEmailsWithLimit(@Param("limit") Long limit);

    boolean existsByEmail(String email);

    Optional<Person> findByEmail(String email);

}
