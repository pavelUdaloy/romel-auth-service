package com.romel.auth.service;

import com.romel.auth.config.prop.JwtProperties;
import com.romel.auth.config.prop.TokenTtlProperties;
import com.romel.auth.domain.cache.AccessToken;
import com.romel.auth.domain.cache.RefreshToken;
import com.romel.auth.repo.AccessTokenRepository;
import com.romel.auth.repo.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProperties jwtProperties;
    private final TokenTtlProperties tokenTtlProperties;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Pair<String, String> createNewPair(String login) {
        UUID uuid = UUID.randomUUID();
        String accessToken = createAccessToken(uuid, login);
        String refreshToken = createRefreshToken(uuid, login);
        return Pair.of(accessToken, refreshToken);
    }

    private String createAccessToken(UUID uuid, String login) {
        String token = createToken("accessToken-" + login, tokenTtlProperties.getAccess());

        AccessToken accessToken = new AccessToken(uuid, login, token);
        AccessToken savedAccessToken = accessTokenRepository.save(accessToken);

        return savedAccessToken.getToken();
    }

    private String createRefreshToken(UUID uuid, String login) {
        String token = createToken("refreshToken-" + login, tokenTtlProperties.getRefresh());

        RefreshToken refreshToken = new RefreshToken(uuid, login, token);
        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);

        return savedToken.getToken();
    }

    private String createToken(String subject, Long plusSecondsToExpare) {
        LocalDateTime issuedAt = LocalDateTime.now().plusSeconds(plusSecondsToExpare);
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)))
                .setIssuer(jwtProperties.getTokenIssuer())
                .setSubject(subject)
                .setIssuedAt(Date.from(issuedAt.toInstant(ZoneOffset.UTC)))
                .claim("account_type", "romel_user")
                .compact();

        return jwtProperties.getTokenPrefix() + token;
    }
}
