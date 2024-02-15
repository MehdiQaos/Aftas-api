package dev.mehdi.aftas.repository;

import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.domain.model.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.isValid = false WHERE t.member = ?1 AND t.isValid = true")
    void revokeAllByUser(Member member);
}