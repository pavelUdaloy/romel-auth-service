package com.romel.auth.service;

import com.romel.auth.config.prop.JwtProperties;
import com.romel.auth.domain.AccessToken;
import com.romel.auth.domain.RefreshToken;
import com.romel.auth.repo.AccessTokenRepository;
import com.romel.auth.repo.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProperties jwtProperties;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Pair<String, String> createNewPair(String login) {
        String accessToken = createAccessToken(login);
        String refreshToken = createRefreshToken(login);
        return Pair.of(accessToken, refreshToken);
    }

    private String createAccessToken(String login) {
        AccessToken accessToken = new AccessToken();
        accessToken.setLogin(login);

        LocalDateTime oneHour = LocalDateTime.now()
                .plusHours(1);
        accessToken.setEndDate(Timestamp.valueOf(oneHour));

        String token = createToken("accessToken-" + login, oneHour);
        accessToken.setToken(token);

        AccessToken savedToken = accessTokenRepository.save(accessToken);
        return savedToken.getToken();
    }

    private String createRefreshToken(String login) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setLogin(login);

        LocalDateTime oneDay = LocalDateTime.now()
                .plusDays(1);
        refreshToken.setEndDate(Timestamp.valueOf(oneDay));

        String token = createToken("refreshToken-" + login, oneDay);
        refreshToken.setToken(token);

        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
        return savedToken.getToken();
    }

    private String createToken(String subject, LocalDateTime endDate) {
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)))
                .issuer(jwtProperties.getTokenIssuer())
                .subject(subject)
                .issuedAt(Date.from(Instant.from(endDate)))
                .claim("account_type", "romel_user")
                .compact();

        return jwtProperties.getTokenPrefix() + token;
    }
}
