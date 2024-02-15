package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.config.JwtProperties;
import dev.mehdi.aftas.domain.model.Token;
import dev.mehdi.aftas.service.JwtService;
import dev.mehdi.aftas.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private final TokenService tokenService;
    private final JwtProperties jwtProperties;

    public JwtServiceImpl(
            TokenService tokenService, JwtProperties jwtProperties
    ) {
        this.tokenService = tokenService;
        this.jwtProperties = jwtProperties;
    }

    private String buildToken(UserDetails userDetails, Map<String, Object> extraClaims, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(userDetails, extraClaims, jwtProperties.getExpiration());
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails, Map.of(), jwtProperties.getRefreshExpiration());
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return generateToken(claims, userDetails);
    }

    @Override
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        boolean isTokenValid = tokenService.getByToken(jwtToken)
                .map(Token::getIsValid)
                .orElse(false);
        String username = extractUsername(jwtToken);
        return userDetails.getUsername().equals(username) &&
                                !isTokenExpired(jwtToken) && isTokenValid;
    }

    @Override
    public boolean isRefreshTokenValid(String refreshToken, UserDetails userDetails) {
        String username = extractUsername(refreshToken);
        return userDetails.getUsername().equals(username) && !isTokenExpired(refreshToken);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
