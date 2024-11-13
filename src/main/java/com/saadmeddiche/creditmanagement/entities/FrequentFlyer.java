package com.saadmeddiche.creditmanagement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Getter @Setter
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "D_FREQUENT_FLAYER")
public class FrequentFlyer extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "frequentflayer_seq_generator")
    @GenericGenerator(name = "frequentflayer_seq_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "d_freqflayer_seq") })
    private Long id;
    private String ffnNo;
    private String tierLevel;
    @OneToOne(mappedBy = "ffnNumber", cascade=CascadeType.ALL)
    @JoinColumn(name = "passanger_id", foreignKey = @ForeignKey(name = "FK_FFLAYER_PASSNG"))
    private Passanger passanger;
    private Integer miles;
}
