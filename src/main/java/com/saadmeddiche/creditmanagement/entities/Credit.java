package com.saadmeddiche.creditmanagement.entities;

import com.saadmeddiche.creditmanagement.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Credit {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_seq")
    private Long id;

    @ManyToOne @JoinColumn(nullable = false , updatable = false)
    private Person person;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false) @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(length = 250)
    private String reason;

    private LocalDateTime grantDate;

    private LocalDateTime refundDate;

}
