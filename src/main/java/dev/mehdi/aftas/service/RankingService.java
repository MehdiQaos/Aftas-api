package dev.mehdi.aftas.service;

import dev.mehdi.aftas.domain.model.Ranking;

import java.util.List;
import java.util.Optional;

public interface RankingService {
    Optional<Ranking> findById(Long id);
    Ranking save(Ranking ranking);
    Integer updateScore(Long rankingId);
    Integer updateScore(Ranking ranking);
    Ranking updateMemberRank(Long rankingId);
    Ranking updateMemberRank(Ranking ranking);

    List<Ranking> updateRankings(List<Ranking> rankings);

    List<Ranking> getRankingsByCompetitionId(Long competitionId);
}
