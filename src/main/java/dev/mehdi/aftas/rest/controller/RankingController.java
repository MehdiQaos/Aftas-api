package dev.mehdi.aftas.rest.controller;

import dev.mehdi.aftas.dto.ranking.RankingMemberDto;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.service.CompetitionService;
import dev.mehdi.aftas.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;
    private final CompetitionService competitionService;

    @GetMapping("{competitionId}")
    public List<RankingMemberDto> byCompetition(@PathVariable Long competitionId) {
        competitionService.findById(competitionId)
            .orElseThrow(() -> new ResourceNotFoundException("Competition not found with id: " + competitionId));
        return rankingService
                .getRankingsByCompetitionId(competitionId)
                .stream().map(RankingMemberDto::fromModels).toList();
    }
}
