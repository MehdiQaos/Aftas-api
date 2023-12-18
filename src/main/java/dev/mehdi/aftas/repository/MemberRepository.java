package dev.mehdi.aftas.repository;

import dev.mehdi.aftas.domain.enums.IdentityDocumentType;
import dev.mehdi.aftas.domain.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdentityNumberAndIdentityDocument(String identityNumber
            , IdentityDocumentType identityDocument);
    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);

    @Query(
        "SELECT m FROM Member m " +
        "JOIN Ranking r ON m.id = r.member.id " +
        "JOIN Competition c ON r.competition.id = c.id " +
        "WHERE c.id = ?1"
    )
    Page<Member> findAllMembersByCompetitionId(Long competitionId, Pageable pageable);

    List<Member> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
