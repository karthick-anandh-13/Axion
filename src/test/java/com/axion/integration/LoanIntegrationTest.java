package com.axion.integration;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoanIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(
            username = "admin@axion.com",
            roles = {"ADMIN"}
    )
    void shouldReturnNotFoundForInvalidOffer() throws Exception {

        UUID invalidOfferId = UUID.randomUUID();

        mockMvc.perform(
                        post("/api/v1/loans/accept/{offerId}", invalidOfferId)
                )
                .andExpect(status().isNotFound());
    }
}