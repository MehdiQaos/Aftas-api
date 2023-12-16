package dev.mehdi.aftas.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(
            mappedBy = "competition",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Ranking> rankings = new ArrayList<>();

    public void addRanking(Ranking ranking) {
        rankings.add(ranking);
        ranking.setCompetition(this);
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", location='" + location + '\'' +
                '}';
    }
}