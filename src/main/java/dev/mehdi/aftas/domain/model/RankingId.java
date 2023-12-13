package dev.mehdi.aftas.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class RankingId implements Serializable {
    private Long memberId;

    private Long competitionId;
}
