package com.romel.auth.facade;

import com.romel.auth.http.dto.RegisterResponse;
import com.romel.auth.service.AccountService;
import com.romel.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final TokenService tokenService;
    private final AccountService accountService;

    public RegisterResponse register(String login, String password) {
        boolean access = accountService.register(login, password);

        if (!access) {
            throw new RuntimeException("Register error");
        }

        Pair<String, String> tokens = tokenService.createNewPair(login);

        return RegisterResponse.builder()
                .accessToken(tokens.getFirst())
                .refreshToken(tokens.getSecond())
                .build();
    }
}
