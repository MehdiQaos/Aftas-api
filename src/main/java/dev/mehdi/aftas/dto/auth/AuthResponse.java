package dev.mehdi.aftas.dto.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
}