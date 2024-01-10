package dev.mehdi.aftas.config.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mehdi.aftas.dto.fish.FishRequestDto;
import dev.mehdi.aftas.service.FishService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
@AllArgsConstructor
public class FishInitializer implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final FishService fishService;

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("seeders/fishes.json");
        FishRequestDto[] fishes = objectMapper.readValue(resource.getInputStream(), FishRequestDto[].class);
        List<FishRequestDto> fishesDtos = Arrays.stream(fishes).filter(fish ->
                        fishService.findByName(fish.getName()).isEmpty()).toList();
        fishService.saveAll(fishesDtos);
    }
}
