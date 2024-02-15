package dev.mehdi.aftas.service;


import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.domain.model.Token;

import java.util.Optional;

public interface TokenService {
    Token save(Token token);
    void revokeAllTokens(Member member);
    Optional<Token> getByToken(String token);
}
