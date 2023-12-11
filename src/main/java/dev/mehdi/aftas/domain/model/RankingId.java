package dev.mehdi.aftas.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RankingId implements Serializable {
    private Long memberId;

    private Long competitionId;
}
