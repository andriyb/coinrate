package com.codingchallenge.coinrate.rategateway.service;

import com.codingchallenge.coinrate.rategateway.config.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The Service for custom user authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder encoder;

    @Autowired
    public CustomUserDetailsService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Load custom user by username.
     *
     * @param username The username.
     * @return The custom user details.
     * @throws UsernameNotFoundException if user not found with this username.
     */
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new CustomUserDetails(username, encoder.encode("123"), true, true, true, true, null);
    }
}
