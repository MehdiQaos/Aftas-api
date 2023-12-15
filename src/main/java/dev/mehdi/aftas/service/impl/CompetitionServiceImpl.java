package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.model.*;
import dev.mehdi.aftas.dto.competition.CompetitionHuntingsDto;
import dev.mehdi.aftas.dto.competition.CompetitionRequestDto;
import dev.mehdi.aftas.dto.hunting.HuntingInitializationDto;
import dev.mehdi.aftas.exception.InvalidRequestException;
import dev.mehdi.aftas.exception.ResourceExistException;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.repository.CompetitionRepository;
import dev.mehdi.aftas.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final MemberService memberService;
    private final RankingService rankingService;
    private final HuntingService huntingService;
    private final FishService fishService;
    private DateTimeFormatter competitionCodeDateFormatter = DateTimeFormatter.ofPattern("yy-MM-dd");

    @Override
    public Optional<Competition> findById(Long id) {
        return competitionRepository.findById(id);
    }

    @Override
    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    @Override
    public Competition create(Competition competition) {
        String code = competition.getLocation().substring(0, 3).toUpperCase() +
                competition.getDate().format(competitionCodeDateFormatter);
        competition.setCode(code);

        validateCompetitionCreation(competition);
        return competitionRepository.save(competition);
    }

    private void validateCompetitionCreation(Competition competition) {
        if (competitionRepository.existsByDate(competition.getDate())) {
            throw new ResourceExistException("Competition in this date already exists");
        }
        LocalTime startTime = competition.getStartTime();
        LocalTime endTime = competition.getEndTime();
        if (startTime.isAfter(endTime)) {
            throw new ResourceNotFoundException("Start time must be less than end time");
        }
    }

    @Override
    public Competition create(CompetitionRequestDto competitionDto) {
        Competition competition = competitionDto.toCompetition();
        return create(competition);
    }

    @Override
    public Competition save(Competition competition) {
        return competitionRepository.save(competition);
    }

    @Override
    public Competition save(CompetitionHuntingsDto competition) {
        return null;
    }

    @Override
    public Ranking registerMember(Long competitionId, Long memberId) {
        Competition competition = findById(competitionId)
            .orElseThrow(() -> InvalidRequestException.of("competition"
                    , "Competition not found with id: " + competitionId));

        Member member = memberService.findById(memberId)
            .orElseThrow(() -> InvalidRequestException.of("member"
                    , "Member not found with id: " + memberId));

        Ranking ranking = Ranking.builder()
                .competition(competition)
                .member(member)
                .build();

        validateRegistration(ranking);
        return rankingService.save(ranking);
    }

    private void validateRegistration(Ranking ranking) {
        Map<String, String> errors = new HashMap<>();
        Member member = ranking.getMember();
        Competition competition = ranking.getCompetition();
        LocalTime startTime = competition.getStartTime();

        LocalDateTime dateTimeStart = competition.getDate().atTime(startTime);
        if (LocalDateTime.now().plusHours(24).isAfter(dateTimeStart)) {
            errors.put("competition", "Registration is closed");
        }
        if (competition.getRankings().stream().anyMatch(r -> r.getMember().equals(member))) {
            errors.put("member", "Member already registered");
        }
        if (!errors.isEmpty()) {
            throw new InvalidRequestException("Invalid request", errors);
        }
    }

    @Override
    public List<Competition> saveAll(List<Competition> competitions) {
        List<Competition> savedCompetitions = competitionRepository.saveAll(competitions);
        savedCompetitions.forEach(competition -> {
            competition.getRankings().forEach(ranking -> {
                rankingService.updateScore(ranking);
                rankingService.updateMemberRank(ranking);
            });
        });
        return savedCompetitions;
    }

    @Override
    public List<Competition> saveCompetitionHuntingAll(List<CompetitionHuntingsDto> competitionsDto) {
        List<Competition> competitions = new ArrayList<>();
        competitionsDto.forEach(competitionDto -> {
            Competition competition = competitionDto.toCompetition();

            Map<Integer, List<HuntingInitializationDto>> membersHuntings = competitionDto.getMemberHuntings();
            membersHuntings.forEach((memberId, huntingsList) -> {
                Member member = memberService.findById(memberId.longValue())
                        .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
                Ranking ranking = Ranking.builder()
                        .member(member)
                        .build();
                huntingsList.forEach(huntingInitializationDto -> {
                    Fish fish = fishService.findById(huntingInitializationDto.getFishId())
                            .orElseThrow(() -> new ResourceNotFoundException("Fish not found"));
                    Hunting hunting = Hunting.builder()
                            .fish(fish)
                            .numberOfFishes(huntingInitializationDto.getNumberOfFishes())
                            .build();
                    ranking.addHunting(hunting);
                });
                competition.addRanking(ranking);
            });
            competitions.add(competition);
        });
        saveAll(competitions);

        return competitions;
    }

    @Override
    public Competition deleteById(Long id) {
        Competition competition = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competition not found with id: " + id));
        competitionRepository.delete(competition);
        return competition;
    }
}
