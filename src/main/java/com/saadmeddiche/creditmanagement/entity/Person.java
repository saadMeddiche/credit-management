package com.saadmeddiche.creditmanagement.entity;

import com.saadmeddiche.creditmanagement.entity.embeddable.PhoneNumber;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Formula("concat(first_name, ' ', last_name)")
    private String fullName;

    @Column(length = 100,unique = false)
    private String email;

    @ElementCollection
    private List<PhoneNumber> phoneNumbers;

    @Column(length = 50)
    private String job;

    @Column(length = 5000)
    private String description;

}
