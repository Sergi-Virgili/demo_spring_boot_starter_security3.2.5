package com.f5.securitybasic.controllers;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "username mustn't be blank")
        String username,
        @NotBlank
        String password ) {
}
