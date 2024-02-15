package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.enums.TokenType;
import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.domain.model.Token;
import dev.mehdi.aftas.dto.auth.AuthRequest;
import dev.mehdi.aftas.dto.auth.AuthResponse;
import dev.mehdi.aftas.dto.member.MemberRequestDto;
import dev.mehdi.aftas.exception.InvalidRequestException;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.service.AuthService;
import dev.mehdi.aftas.service.JwtService;
import dev.mehdi.aftas.service.MemberService;
import dev.mehdi.aftas.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {
    private final MemberService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthServiceImpl(
            MemberService userService,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            TokenService tokenService
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword()
            )
        );
        Member member = userService.getByEmail(authRequest.getEmail())
                .orElseThrow(
                    () -> new ResourceNotFoundException("Member not found")
                );
        tokenService.revokeAllTokens(member);
        String jwtToken = createToken(member);
        String refreshToken = jwtService.generateRefreshToken(member);

        return authenticationResponse(jwtToken, refreshToken);
    }

    private String createToken(Member member) {
        String jwtToken = jwtService.generateToken(member);
        Token token = Token.builder()
                .member(member)
                .token(jwtToken)
                .isValid(true)
                .type(TokenType.BEARER)
                .build();
        tokenService.save(token);
        return jwtToken;
    }

    @Override
    public AuthResponse register(MemberRequestDto memberRequest) {
        String hashedPassword = passwordEncoder.encode(memberRequest.getPassword());
        memberRequest.setPassword(hashedPassword);
        Member member = userService.save(memberRequest);
        String jwtToken = createToken(member);
        String refreshToken = jwtService.generateRefreshToken(member);

        return authenticationResponse(jwtToken, refreshToken);
    }

    @Override
    public AuthResponse refresh(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw InvalidRequestException.of("Token", "Not provided");
        }

        String refreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw InvalidRequestException.of("Email", "invalid");
        }
        Member member = userService.getByEmail(userEmail).orElseThrow(
                    () -> new ResourceNotFoundException("Member not found")
                );
        if (!jwtService.isRefreshTokenValid(refreshToken, member)) {
            throw InvalidRequestException.of("Token", "invalid");
        }

        tokenService.revokeAllTokens(member);
        String jwtToken = jwtService.generateToken(member);
        return authenticationResponse(jwtToken, refreshToken);
    }

    private AuthResponse authenticationResponse(String token, String refreshToken) {
        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }
}
