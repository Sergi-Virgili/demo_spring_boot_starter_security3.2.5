package com.f5.securitybasic.controllers;

import com.f5.securitybasic.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@PreAuthorize("denyAll()")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/open")
    public String openEndpoint() {
        return "Hello in OPEN ENDPOINT";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/closed")
    public String closedEndpoint() {
        return "Hello you are in the Restricted Area";
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authService.register(request));
    }

}
