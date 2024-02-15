package dev.mehdi.aftas.dto.member;

import dev.mehdi.aftas.domain.enums.IdentityDocumentType;
import dev.mehdi.aftas.domain.model.Member;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class MemberResponseDto {
    private Long id;

    private String firstName;

    private String lastName;
    private String email;

    private String identityNumber;

    private String nationality;

    private LocalDate birthDate;

    private IdentityDocumentType identityDocument;

    public static MemberResponseDto fromModel(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getIdentityNumber(),
                member.getNationality(),
                member.getBirthDate(),
                member.getIdentityDocument()
        );
    }
}
