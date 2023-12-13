package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.model.Ranking;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.repository.RankingRepository;
import dev.mehdi.aftas.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;

    @Override
    public Optional<Ranking> findById(Long id) {
        return rankingRepository.findById(id);
    }

    @Override
    public Ranking save(Ranking ranking) {
        return rankingRepository.save(ranking);
    }

    @Override
    public Integer updateScore(Long rankingId) {
        Ranking ranking = findById(rankingId)
            .orElseThrow(() -> new ResourceNotFoundException("Ranking not found"));
        return updateScore(ranking);
    }

    @Override
    public Integer updateScore(Ranking ranking) {
        Integer score = ranking.getHuntings().stream()
                .mapToInt(hunting -> hunting.getNumberOfFishes() *
                        hunting.getFish().getLevel().getPoints())
                .sum();
        ranking.setScore(score);
        save(ranking);
        return score;
    }

    @Override
    public Integer updateMemberRank(Long rankingId) {
        Ranking ranking = findById(rankingId)
                .orElseThrow(() -> new ResourceNotFoundException("Ranking not found"));
        return updateMemberRank(ranking);
    }

    @Override
    public Integer updateMemberRank(Ranking ranking) {
        int score = updateScore(ranking);
        Integer rank = ranking.getCompetition().getRankings().stream()
                .filter(r -> updateScore(r) > score)
                .mapToInt(r -> 1)
                .sum() + 1;
        ranking.setMemberRank(rank);
        save(ranking);
        return rank;
    }
}
