package dev.mehdi.aftas.repository;

import dev.mehdi.aftas.domain.enums.IdentityDocumentType;
import dev.mehdi.aftas.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdentityNumberAndIdentityDocument(String identityNumber
            , IdentityDocumentType identityDocument);
    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);
}
