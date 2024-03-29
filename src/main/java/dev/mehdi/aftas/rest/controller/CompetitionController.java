package dev.mehdi.aftas.rest.controller;

import dev.mehdi.aftas.domain.enums.CompetitionStatus;
import dev.mehdi.aftas.domain.model.Competition;
import dev.mehdi.aftas.domain.model.Hunting;
import dev.mehdi.aftas.dto.competition.CompetitionRequestDto;
import dev.mehdi.aftas.dto.competition.CompetitionResponseDto;
import dev.mehdi.aftas.dto.hunting.HuntingRequestDto;
import dev.mehdi.aftas.dto.member.MemberRankingDto;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.service.CompetitionService;
import dev.mehdi.aftas.service.HuntingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/competition")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;
    private final HuntingService huntingService;

    @GetMapping
    ResponseEntity<Page<CompetitionResponseDto>> all(Pageable pageable, @RequestParam(required = false, defaultValue = "ALL") String filter) {
        Page<Competition> competitionsPage =
                competitionService.findAllWithPaginationAndSortingAndFilter(pageable, filter);
        Page<CompetitionResponseDto> competitionsDtoPage =
            competitionsPage
                .map(cmp -> {
                    CompetitionResponseDto dto = CompetitionResponseDto.from(cmp);
                    CompetitionStatus status = competitionService.getCompetitionStatus(cmp);
                    dto.setStatus(status);
                    return dto;
                });

        return ResponseEntity.ok(competitionsDtoPage);
    }

    @GetMapping("{id}")
    ResponseEntity<CompetitionResponseDto> one(@PathVariable Long id) {
        Competition competition = competitionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competition not found with id: " + id));

        CompetitionResponseDto competitionResponseDto =
                CompetitionResponseDto.from(competition);
        CompetitionStatus status = competitionService.getCompetitionStatus(competition);
        competitionResponseDto.setStatus(status);

        return ResponseEntity.ok(competitionResponseDto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JURY')")
    ResponseEntity<CompetitionResponseDto> create(
            @RequestBody @Valid CompetitionRequestDto competitionDto) {

        Competition competition = competitionService.create(competitionDto);
        CompetitionResponseDto competitionResponseDto =
                CompetitionResponseDto.from(competition);
        CompetitionStatus status = competitionService.getCompetitionStatus(competition);
        competitionResponseDto.setStatus(status);

        return ResponseEntity.ok(competitionResponseDto);
    }

    @GetMapping("{id}/members")
    ResponseEntity<Page<MemberRankingDto>> getMembers(
            @PathVariable Long id,
            Pageable pageable
    ) {
        return ResponseEntity
                .ok(competitionService.findAllMembersByCompetitionId(id, pageable));
    }

    @GetMapping("{competitionId}/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JURY')")
    ResponseEntity<Boolean> registerMember(
            @PathVariable Long competitionId, @PathVariable Long memberId
    ) {

        competitionService.registerMember(competitionId, memberId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JURY')")
    ResponseEntity<Integer> addHunting(@PathVariable Long id,
                                       @RequestBody @Valid HuntingRequestDto huntingDto) {

        Hunting hunting = huntingService.addHunt(huntingDto);
        return new ResponseEntity<>(hunting.getNumberOfFishes(), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<CompetitionResponseDto> delete(@PathVariable Long id) {
        Competition competition = competitionService.deleteById(id);
        CompetitionResponseDto competitionResponseDto =
                CompetitionResponseDto.from(competition);
        CompetitionStatus status = competitionService.getCompetitionStatus(competition);
        competitionResponseDto.setStatus(status);
        return ResponseEntity.ok(competitionResponseDto);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompetitionResponseDto> update(@PathVariable Long id, @RequestBody @Valid CompetitionRequestDto competitionDto) {
        Competition updatedCompetition = competitionService.update(id, competitionDto);
        CompetitionResponseDto competitionResponseDto =
                CompetitionResponseDto.from(updatedCompetition);
        CompetitionStatus status = competitionService.getCompetitionStatus(updatedCompetition);
        competitionResponseDto.setStatus(status);
        return ResponseEntity.ok(competitionResponseDto);
    }
}
