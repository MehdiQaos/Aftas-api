package dev.mehdi.aftas.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Hunting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numberOfFishes;

    @ManyToOne
    private Fish fish;

    @ManyToOne
    private Ranking ranking;
}
