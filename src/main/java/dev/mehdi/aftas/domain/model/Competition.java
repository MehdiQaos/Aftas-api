package dev.mehdi.aftas.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    @OneToMany(mappedBy = "competition", fetch = FetchType.LAZY)
    private final Set<Ranking> rankings = new HashSet<>();
}