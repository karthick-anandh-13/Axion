package com.axion.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI axionOpenAPI() {

        return new OpenAPI()

                .info(new Info()
                        .title("Axion Loan Management API")
                        .description("""
                                Enterprise Loan Management & Compliance Platform

                                Features:
                                • Borrower Management
                                • Lending Partners
                                • AI Matching Engine
                                • Loan Offers
                                • EMI Payments
                                • Ledger Accounting
                                """)
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Karthick Anandh")
                                .email("support@axion.ai"))
                        .license(new License()
                                .name("Axion Internal License")))

                .externalDocs(new ExternalDocumentation()
                        .description("Axion Documentation"));
    }
}