package dev.mehdi.aftas.domain.model;

import dev.mehdi.aftas.domain.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TokenType type;
    private String token;
    private Boolean isValid;

    @ManyToOne
    private Member member;
}
