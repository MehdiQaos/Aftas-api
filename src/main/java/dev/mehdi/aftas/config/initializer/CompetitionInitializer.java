package dev.mehdi.aftas.config.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mehdi.aftas.dto.competition.CompetitionHuntingsDto;
import dev.mehdi.aftas.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
//@Order(5)
@RequiredArgsConstructor
public class CompetitionInitializer implements CommandLineRunner {

    private final CompetitionService competitionService;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("seeders/competitions.json");
        CompetitionHuntingsDto[] competitionsDto = objectMapper.readValue(resource.getInputStream(), CompetitionHuntingsDto[].class);
        competitionService.saveCompetitionHuntingAll(List.of(competitionsDto));
    }
}
