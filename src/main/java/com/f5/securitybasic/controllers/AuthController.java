package com.f5.securitybasic.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AuthController {

    @GetMapping("/open")
    public String openEndpoint() {
        return "Hello in OPEN ENDPONT";
    }

    @GetMapping("/closed")
    public String closedEndpoint() {
        return "Hello you are in the Restricted Area";
    }

}
