package dev.mehdi.aftas.service;

import dev.mehdi.aftas.domain.enums.CompetitionStatus;
import dev.mehdi.aftas.domain.model.Competition;
import dev.mehdi.aftas.domain.model.Ranking;
import dev.mehdi.aftas.dto.competition.CompetitionHuntingsDto;
import dev.mehdi.aftas.dto.competition.CompetitionRequestDto;
import dev.mehdi.aftas.dto.member.MemberRankingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompetitionService {
    Optional<Competition> findById(Long id);
    Page<Competition> findAllWithPaginationAndSortingAndFilter(Pageable pageable, String filter);
    Competition create(Competition competition);
    Competition create(CompetitionRequestDto competitionDto);
    Competition save(Competition competition);
    Competition save(CompetitionHuntingsDto competition);
    Competition update(Long id, CompetitionRequestDto competitionDto);
    Ranking registerMember(Long competitionId, Long memberId);
    List<Competition> saveAll(List<Competition> competitions);
    List<Competition> saveCompetitionHuntingAll(List<CompetitionHuntingsDto> competitions);
    Competition deleteById(Long id);
    CompetitionStatus getCompetitionStatus(Competition competition);

    Page<MemberRankingDto> findAllMembersByCompetitionId(
            Long competitionId, Pageable pageable);
}
