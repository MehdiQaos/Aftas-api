package dev.mehdi.aftas.dto.member;

import dev.mehdi.aftas.domain.enums.IdentityDocumentType;
import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.domain.model.Ranking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter @Getter
@AllArgsConstructor
public class MemberRankingDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String identityNumber;
    private String nationality;
    private LocalDate birthDate;
    private IdentityDocumentType identityDocument;
    private Integer score;
    private Integer rank;
    private Long competitionId;

    public static MemberRankingDto fromModels(Member member, Ranking ranking) {
        return new MemberRankingDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getIdentityNumber(),
                member.getNationality(),
                member.getBirthDate(),
                member.getIdentityDocument(),
                ranking.getScore(),
                ranking.getMemberRank(),
                ranking.getCompetition().getId()
        );
    }
}
