package dev.mehdi.aftas.service;

import dev.mehdi.aftas.domain.model.Ranking;

import java.util.Optional;

public interface RankingService {
    Optional<Ranking> findById(Long id);
    Ranking save(Ranking ranking);
    Integer updateScore(Long rankingId);
    Integer updateScore(Ranking ranking);
    Integer updateMemberRank(Long rankingId);
    Integer updateMemberRank(Ranking ranking);
}
