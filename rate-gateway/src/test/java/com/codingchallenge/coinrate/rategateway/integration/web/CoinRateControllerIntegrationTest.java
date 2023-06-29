package com.codingchallenge.coinrate.rategateway.integration.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoinRateControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testDetectIp() {
        // Create a user with appropriate roles
        UserDetails userDetails = User.withUsername("test")
                .password("123")
                .roles("USER")
                .build();

        // Simulate authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Set up a request entity without a request body
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/detect-ip", HttpMethod.POST, requestEntity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // Assert other expectations based on the response body

        // Reset authentication
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testGetCoinRateNotFound() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/update-current-rate/{coin}", String.class, "coin404");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testUpdateCoinRate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>("{\"rate\": 5000}", headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/update-current-rate", HttpMethod.PUT, requestEntity, String.class, "bitcoin");
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }


    @Test
    public void testUpdateCoinRateBadRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Invalid JSON payload
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"invalid\": 5000}", headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/update-current-rate/{coin}", HttpMethod.PUT, requestEntity, String.class, "bitcoin");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void testGetCoinRateUnauthorized() {
        // Perform request without authentication
        TestRestTemplate unauthorizedRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response =
                unauthorizedRestTemplate.getForEntity("/update-current-rate/{coin}", String.class, "bitcoin");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

    }
}
