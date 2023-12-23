package dev.mehdi.aftas.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Competition competition;

    private Integer score;

    private Integer memberRank;

    @OneToMany(mappedBy = "ranking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Hunting> huntings = new ArrayList<>();

    public void addHunting(Hunting hunting) {
        huntings.add(hunting);
        hunting.setRanking(this);
    }
}