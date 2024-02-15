package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.domain.model.Token;
import dev.mehdi.aftas.repository.TokenRepository;
import dev.mehdi.aftas.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(
            TokenRepository tokenRepository
    ) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    public Optional<Token> getByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void revokeAllTokens(Member member) {
        tokenRepository.revokeAllByUser(member);
    }
}
