package dev.mehdi.aftas.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class AuthRequest {
    @NotBlank(message = "email required")
    private String email;

    @NotBlank(message = "password required")
    private String password;
}
