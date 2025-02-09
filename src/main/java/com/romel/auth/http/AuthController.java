package com.romel.auth.http;

import com.romel.auth.facade.AuthFacade;
import com.romel.auth.http.dto.RegisterRequest;
import com.romel.auth.http.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/romel/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        return authFacade.register(registerRequest.getLogin(), registerRequest.getPassword());
    }

    @PostMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/refresh")
    public String refresh() {
        return "refresh";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
