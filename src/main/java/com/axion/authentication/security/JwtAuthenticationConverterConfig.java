package com.axion.authentication.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class JwtAuthenticationConverterConfig {

    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken>
            jwtAuthenticationConverter() {

        return jwt -> {

            String roles = jwt.getClaimAsString("roles");

            Collection<SimpleGrantedAuthority> authorities =
                    roles == null || roles.isBlank()
                            ? java.util.List.of()
                            : Arrays.stream(roles.split(" "))
                                    .map(role -> "ROLE_" + role)
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toSet());

            return new JwtAuthenticationToken(
                    jwt,
                    authorities
            );
        };
    }
}