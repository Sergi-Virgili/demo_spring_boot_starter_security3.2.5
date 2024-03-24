package com.f5.securitybasic.controllers;

import java.util.List;

public record AuthResponse(String token, String username, List<String> roles) {
}
