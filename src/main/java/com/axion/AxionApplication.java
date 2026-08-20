package com.axion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.axion.authentication.security.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class AxionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AxionApplication.class, args);
    }
}
