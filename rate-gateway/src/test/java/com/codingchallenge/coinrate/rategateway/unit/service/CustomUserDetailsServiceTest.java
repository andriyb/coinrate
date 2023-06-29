package com.codingchallenge.coinrate.rategateway.unit.service;

import com.codingchallenge.coinrate.rategateway.service.CustomUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("CustomUserDetailsService tests")
class CustomUserDetailsServiceTest {

    @Mock
    private PasswordEncoder encoder;

    @Test
    @DisplayName("Load user by username")
    void testLoadUserByUsername() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        CustomUserDetailsService service = new CustomUserDetailsService(encoder);
        String username = "user";

        // Mock the behavior of the password encoder
        String encodedPassword = "encoded123";
        when(encoder.encode("123")).thenReturn(encodedPassword);

        // Act
        UserDetails userDetails = service.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(encodedPassword, userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isAccountNonLocked());

        // Verify that the password encoder was invoked
        verify(encoder).encode("123");
    }

    @Test
    @DisplayName("Load user by unknown username")
    void testLoadUserByUnknownUsername() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        CustomUserDetailsService service = new CustomUserDetailsService(encoder);
        String unknownUsername = "unknown";

        // Mock the behavior of the password encoder
        when(encoder.encode("123")).thenReturn("encoded123");

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(unknownUsername);
        });

        // Verify that the password encoder was not invoked
        verify(encoder, never()).encode(anyString());
    }
}
