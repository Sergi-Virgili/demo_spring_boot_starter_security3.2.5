package com.f5.securitybasic.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@PreAuthorize("denyAll()")
public class AuthController {
    @PreAuthorize("permitAll()")
    @GetMapping("/open")
    public String openEndpoint() {
        return "Hello in OPEN ENDPOINT";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/closed")
    public String closedEndpoint() {
        return "Hello you are in the Restricted Area";
    }

}
