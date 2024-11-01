package com.saadmeddiche.creditmanagement.entities;

import com.saadmeddiche.creditmanagement.entities.embeddables.PhoneNumber;
import com.saadmeddiche.creditmanagement.entities.listeners.PersonListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@EntityListeners(PersonListener.class)
public class Person implements Serializable {

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(uniqueConstraints = @UniqueConstraint(columnNames = {"number", "country_code"}))
    private Set<PhoneNumber> phoneNumbers;

    @Column(length = 50)
    private String job;

    @Column(length = 5000)
    private String description;

    @OneToMany(mappedBy = "person",fetch = FetchType.EAGER)
    private Set<Credit> credits;

}
