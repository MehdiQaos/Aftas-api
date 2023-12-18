package dev.mehdi.aftas.dto.ranking;

import dev.mehdi.aftas.domain.model.Ranking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RankingMemberDto {
    private Long rankingId;
    private Long memberId;
    private Long competitionId;
    private String firstName;
    private String lastName;

    public static RankingMemberDto fromModels(Ranking ranking) {
        return new RankingMemberDto(
            ranking.getId(),
            ranking.getMember().getId(),
            ranking.getCompetition().getId(),
            ranking.getMember().getFirstName(),
            ranking.getMember().getLastName()
        );
    }
}
