package dev.mehdi.aftas.config.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mehdi.aftas.dto.level.LevelRequestDto;
import dev.mehdi.aftas.service.LevelService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
@AllArgsConstructor
public class LevelInitializer implements CommandLineRunner {
    private final ObjectMapper objectMapper;
    private final LevelService levelService;

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("seeders/levels.json");
        LevelRequestDto[] levels = objectMapper.readValue(resource.getInputStream(), LevelRequestDto[].class);
        levelService.saveAll(List.of(levels));
    }
}
