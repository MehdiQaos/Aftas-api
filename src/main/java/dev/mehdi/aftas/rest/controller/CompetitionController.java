package dev.mehdi.aftas.rest.controller;

import dev.mehdi.aftas.domain.model.Competition;
import dev.mehdi.aftas.domain.model.Hunting;
import dev.mehdi.aftas.dto.competition.CompetitionRequestDto;
import dev.mehdi.aftas.dto.competition.CompetitionResponseDto;
import dev.mehdi.aftas.dto.hunting.HuntingRequestDto;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.service.CompetitionService;
import dev.mehdi.aftas.service.HuntingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competition")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;
    private final HuntingService huntingService;

//    @GetMapping
//    ResponseEntity<List<CompetitionResponseDto>> all() {
//        List<Competition> competitions = competitionService.findAll();
//        List<CompetitionResponseDto> body = competitions.stream()
//                .map(CompetitionResponseDto::from)
//                .toList();
//
//        return ResponseEntity.ok(body);
//    }

    @GetMapping
    ResponseEntity<Page<CompetitionResponseDto>> all(Pageable pageable) {
        Page<Competition> competitionsPage =
                competitionService.findAllWithPaginationAndSorting(pageable);
        Page<CompetitionResponseDto> competitionsDtoPage =
                competitionsPage.map(CompetitionResponseDto::from);

        return ResponseEntity.ok(competitionsDtoPage);
    }

    @GetMapping("{id}")
    ResponseEntity<CompetitionResponseDto> one(@PathVariable Long id) {
        Competition competition = competitionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competition not found with id: " + id));

        CompetitionResponseDto body = CompetitionResponseDto.from(competition);

        return ResponseEntity.ok(body);
    }

    @PostMapping
    ResponseEntity<CompetitionResponseDto> create(
            @RequestBody @Valid CompetitionRequestDto competitionDto) {

        Competition competition = competitionService.create(competitionDto);
        CompetitionResponseDto body = CompetitionResponseDto.from(competition);

        return ResponseEntity.ok(body);
    }

    @GetMapping("{competitionId}/{memberId}")
    ResponseEntity<Boolean> registerMember(
            @PathVariable Long competitionId, @PathVariable Long memberId
    ) {

        competitionService.registerMember(competitionId, memberId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("{id}")
    ResponseEntity<Integer> addHunting(@PathVariable Long id,
                                       @RequestBody @Valid HuntingRequestDto huntingDto) {

        Hunting hunting = huntingService.addHunt(huntingDto);
        return new ResponseEntity<>(hunting.getNumberOfFishes(), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    ResponseEntity<CompetitionResponseDto> delete(@PathVariable Long id) {
        Competition competition = competitionService.deleteById(id);
        CompetitionResponseDto body = CompetitionResponseDto.from(competition);
        return ResponseEntity.ok(body);
    }

    @PutMapping("{id}")
    public ResponseEntity<CompetitionResponseDto> update(@PathVariable Long id, @RequestBody @Valid CompetitionRequestDto competitionDto) {
        Competition updatedCompetition = competitionService.update(id, competitionDto);
        CompetitionResponseDto body = CompetitionResponseDto.from(updatedCompetition);
        return ResponseEntity.ok(body);
    }
}