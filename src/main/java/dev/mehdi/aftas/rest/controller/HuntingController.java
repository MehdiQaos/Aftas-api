package dev.mehdi.aftas.rest.controller;

import dev.mehdi.aftas.domain.model.Hunting;
import dev.mehdi.aftas.dto.hunting.HuntingRequestDto;
import dev.mehdi.aftas.dto.hunting.HuntingResponseDto;
import dev.mehdi.aftas.service.HuntingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hunting")
@RequiredArgsConstructor
public class HuntingController {
    private final HuntingService huntingService;

    @PostMapping
    public ResponseEntity<HuntingResponseDto> add(@RequestBody HuntingRequestDto huntingRequestDto) {
        Hunting hunting = huntingService.addHunt(huntingRequestDto);
        HuntingResponseDto huntingResponseDto = HuntingResponseDto.fromModel(hunting);
        return ResponseEntity.ok(huntingResponseDto);
    }

//    @GetMapping("{competitionId}/{memberId}")
//    public ResponseEntity<List<HuntingResponseDto>> getHuntingByCompetitionAndMember(
//        @PathVariable Long competitionId,
//        @PathVariable Long memberId
//    ) {
//        Hunting hunting = huntingService.findByCompetitionAndMember(competitionId, memberId);
//        HuntingResponseDto huntingResponseDto = HuntingResponseDto.fromModel(hunting);
//        return ResponseEntity.ok(huntingResponseDto);
//    }
}
