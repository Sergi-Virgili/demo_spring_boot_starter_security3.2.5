package com.f5.securitybasic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void openEndpointWithoutAuthenticationPass() throws Exception {
        mockMvc.perform(get("/open"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello in OPEN ENDPOINT")));
    }

    @Test
    void closedEndpointWithBasicAuthentication() throws Exception {
        mockMvc.perform(get("/closed")
                        .with(user("user").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello you are in the Restricted Area")));
    }

    @Test
    void closedEndpointWithWrongBasicAuthentication() throws Exception {
        mockMvc.perform(get("/closed")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk());

    }

    @Test
    void closedEndpointWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/closed"))
                .andExpect(status().isForbidden());
    }
}