package com.saadmeddiche.creditmanagement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "D_PASSENGER")
public class Passanger extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pax_seq_generator")
    @GenericGenerator(name = "pax_seq_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "d_pax_seq") })
    private Long id;

    private String firstName;
    private String lastName;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "frequent_flyer_id")
    private FrequentFlyer ffnNumber;

}
