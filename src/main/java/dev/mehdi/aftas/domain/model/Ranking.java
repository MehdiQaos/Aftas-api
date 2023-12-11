package dev.mehdi.aftas.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Ranking {
    @EmbeddedId
    private RankingId id;

    @ManyToOne
    @MapsId("memberId")
    private Member member;

    @ManyToOne
    @MapsId("competitionId")
    private Competition competition;

    private Integer score;

    private Integer memberRank;

    @OneToMany(mappedBy = "ranking", fetch = FetchType.LAZY)
    private final Set<Hunting> huntings = new HashSet<>();
}