package com.axion.authentication.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.axion.authentication.entity.Role;
import com.axion.authentication.repository.RoleRepository;

@Configuration
public class RoleDataInitializer {

    @Bean
    CommandLineRunner initializeRoles(RoleRepository roleRepository) {

        return args -> {

            createRole(
                    roleRepository,
                    "BORROWER",
                    "User who can apply for and manage loans."
            );

            createRole(
                    roleRepository,
                    "LENDER",
                    "User who can provide funds for loans."
            );

            createRole(
                    roleRepository,
                    "ADMIN",
                    "Platform administrator."
            );

            createRole(
                    roleRepository,
                    "AUDITOR",
                    "User responsible for compliance and audit activities."
            );

            createRole(
                    roleRepository,
                    "SUPER_ADMIN",
                    "Full platform administrator."
            );
        };
    }

    private void createRole(
            RoleRepository roleRepository,
            String name,
            String description) {

        if (!roleRepository.existsByName(name)) {

            Role role = Role.builder()
                    .name(name)
                    .description(description)
                    .build();

            roleRepository.save(role);
        }
    }
}