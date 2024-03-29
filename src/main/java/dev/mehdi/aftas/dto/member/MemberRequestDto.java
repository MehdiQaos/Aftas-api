package dev.mehdi.aftas.dto.member;

import dev.mehdi.aftas.domain.enums.IdentityDocumentType;
import dev.mehdi.aftas.domain.model.Member;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot be longer than 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot be longer than 100 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Identity number is required")
    private String identityNumber;

    @NotNull(message = "Identity document type id is required")
    private Long identityDocumentTypeId;

    public Member toMember() {
        IdentityDocumentType identityDocumentType =
                IdentityDocumentType.values()[identityDocumentTypeId.intValue()];

        return Member.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .nationality(nationality)
                .identityNumber(identityNumber)
                .identityDocument(identityDocumentType)
                .birthDate(birthDate)
                .enabled(false)
                .build();
    }
}