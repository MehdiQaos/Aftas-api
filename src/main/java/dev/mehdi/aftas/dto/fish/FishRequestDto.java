package dev.mehdi.aftas.dto.fish;

import dev.mehdi.aftas.domain.model.Fish;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class FishRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Average weight is required")
    private Long averageWeight;

    @NotBlank(message = "Level id is required")
    private Long levelId;

    public static Fish toFish(FishRequestDto fishRequestDto) {
        return Fish.builder()
                .name(fishRequestDto.getName())
                .averageWeight(fishRequestDto.getAverageWeight())
                .build();
    }
}
