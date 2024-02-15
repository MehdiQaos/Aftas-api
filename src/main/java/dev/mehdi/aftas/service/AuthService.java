package dev.mehdi.aftas.service;

import dev.mehdi.aftas.dto.auth.AuthRequest;
import dev.mehdi.aftas.dto.auth.AuthResponse;
import dev.mehdi.aftas.dto.member.MemberRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);

    AuthResponse register(MemberRequestDto memberRequest);

    AuthResponse refresh(HttpServletRequest request) throws IOException;
}
