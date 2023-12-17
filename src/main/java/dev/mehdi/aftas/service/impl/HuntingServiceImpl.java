package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.model.*;
import dev.mehdi.aftas.dto.hunting.HuntingInitializationDto;
import dev.mehdi.aftas.dto.hunting.HuntingRequestDto;
import dev.mehdi.aftas.exception.InvalidRequestException;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.repository.HuntingRepository;
import dev.mehdi.aftas.service.FishService;
import dev.mehdi.aftas.service.HuntingService;
import dev.mehdi.aftas.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HuntingServiceImpl implements HuntingService {

    private final HuntingRepository huntingRepository;
    private final FishService fishService;
    private final RankingService rankingService;

    @Override
    public Optional<Hunting> findById(Long id) {
        return huntingRepository.findById(id);
    }

    @Override
    public Hunting save(Hunting hunting) {
        return huntingRepository.save(hunting);
    }

    @Override
    public Hunting save(HuntingInitializationDto huntingDto) {
        Fish fish = fishService.findById(huntingDto.getFishId())
                .orElseThrow(() -> new ResourceNotFoundException("Fish not found"));
        Hunting hunting = Hunting.builder()
                .fish(fish)
                .numberOfFishes(huntingDto.getNumberOfFishes())
                .build();
        return huntingRepository.save(hunting);
    }

    @Override
    public Hunting addHunt(HuntingRequestDto huntingDto) {
        Long rankingId = huntingDto.getRankingId();
        Ranking ranking = rankingService.findById(rankingId)
                .orElseThrow(() -> new ResourceNotFoundException("Ranking not found with id: " + rankingId));

        Fish fish = fishService.findById(huntingDto.getFishId())
                .orElseThrow(() -> new ResourceNotFoundException("Fish not found"));

        if (huntingDto.getWeight() < fish.getAverageWeight())
            throw InvalidRequestException.of("weight", "fish weight is below average");

        Competition competition = ranking.getCompetition();
        LocalDateTime startDateTime = competition.getDate().atTime(competition.getStartTime());
        LocalDateTime endDateTime = competition.getDate().atTime(competition.getEndTime());
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(startDateTime) || now.isAfter(endDateTime)) {
            throw InvalidRequestException.of("time", "Competition is not active");
        }

        Hunting hunting;
        Optional<Hunting> huntingOptional =
                huntingRepository.findByFishAndRanking(fish, ranking);

        if (huntingOptional.isPresent()) {
            hunting = huntingOptional.get();
            hunting.setNumberOfFishes(hunting.getNumberOfFishes() + 1);
        } else {
            hunting = Hunting.builder()
                    .fish(fish)
                    .numberOfFishes(1)
                    .ranking(ranking)
                    .build();
        }

        return huntingRepository.save(hunting);
    }
}
