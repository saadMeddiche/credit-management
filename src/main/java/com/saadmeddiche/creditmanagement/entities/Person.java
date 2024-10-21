package com.saadmeddiche.creditmanagement.entities;

import com.saadmeddiche.creditmanagement.entities.embdebles.PhoneNumber;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@Getter @Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Formula("concat(firstName, ' ', lastName)")
    private String fullName;

    @Column(unique = true)
    private String email;

    @ElementCollection
    private List<PhoneNumber> phoneNumbers;

}
