package com.saadmeddiche.creditmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"number", "country_code"}))
public class PhoneNumber implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_number_seq")
    private Long id;

    @Column(length = 50)
    private String number;

    @Column(length = 10)
    private String countryCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("phoneNumbers")
    private Person person;

}
