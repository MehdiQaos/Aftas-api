package dev.mehdi.aftas.dto.fish;

import dev.mehdi.aftas.domain.model.Fish;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class FishRequestDto {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    private String name;

    @NotNull(message = "Average weight is required")
    @Min(value = 1, message = "Average weight must be greater than 0")
    private Float averageWeight;

    @NotNull(message = "Level id is required")
    @Min(value = 1, message = "Level id must be greater than or equal to 1")
    private Long levelId;

    public static Fish toFish(FishRequestDto fishRequestDto) {
        return Fish.builder()
                .name(fishRequestDto.getName())
                .averageWeight(fishRequestDto.getAverageWeight())
                .build();
    }
}
