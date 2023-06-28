package com.codingchallenge.coinrate.rategateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for setting up the Spring Security configuration password encoder.
 */
@Configuration
public class PasswordEncoderConfiguration {

    /**
     * Creates a BCryptPasswordEncoder bean.
     *
     * @return The PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}