package dev.mehdi.aftas.dto.hunting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class HuntingRequestDto {
    private Long rankingId;
    private Long fishId;
}