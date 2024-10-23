package com.saadmeddiche.creditmanagement.entities;

import com.saadmeddiche.creditmanagement.entities.embeddables.PhoneNumber;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Person {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    private Long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Formula("concat(first_name, ' ', last_name)")
    private String fullName;

    @Column(length = 100,unique = true)
    private String email;

    @ElementCollection
    @CollectionTable(uniqueConstraints = @UniqueConstraint(columnNames = {"number", "country_code"}))
    private List<PhoneNumber> phoneNumbers;

    @Column(length = 50)
    private String job;

    @Column(length = 5000)
    private String description;

    @OneToMany(mappedBy = "person")
    private List<Credit> credits;

}
