package dev.mehdi.aftas.dto.member;

import dev.mehdi.aftas.domain.enums.IdentityDocumentType;
import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
    private String firstName;

    private String lastName;

    private String nationality;

    private LocalDate birthDate;

    private String identityNumber;

    private Long identityDocumentTypeId;

    public Member toMember() {
        IdentityDocumentType identityDocumentType =
                IdentityDocumentType.values()[identityDocumentTypeId.intValue()];

        return Member.builder()
                .firstName(firstName)
                .lastName(lastName)
                .nationality(nationality)
                .identityNumber(identityNumber)
                .identityDocument(identityDocumentType)
                .birthDate(birthDate)
                .build();
    }
}