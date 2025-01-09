package com.saadmeddiche.creditmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class PersonFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    private Long id;

    @Column(length = 100, nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileLink;

    @JsonIgnoreProperties("files")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

}
