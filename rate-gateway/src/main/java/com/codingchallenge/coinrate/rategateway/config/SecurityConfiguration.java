package com.codingchallenge.coinrate.rategateway.config;

import com.codingchallenge.coinrate.rategateway.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration class for setting up the Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder encoder;

    @Autowired
    SecurityConfiguration(CustomUserDetailsService customUserDetailsService, PasswordEncoder encoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.encoder = encoder;
    }

    /**
     * Configures the security filter chain.
     *
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs while configuring the filter chain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/favicon.ico").permitAll() // Allow access to the favicon for all requests
                .anyRequest()
                .authenticated() // Require authentication for all other requests
                .and()
                .formLogin()
                .loginPage("/login") // Specify the custom login page URL
                .permitAll() // Allow access to the login page for all users
                .successForwardUrl("/index") // Redirect to "/index" after successful login
                .and()
                .logout()
                .permitAll() // Allow access to the logout URL for all users
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Set the logout URL
                .logoutSuccessUrl("/login") // Redirect to "/login" after successful logout
                .and().logout() // Additional logout configuration
                .logoutUrl("/logout") // Set the logout URL
                .logoutSuccessUrl("/login") // Redirect to "/login" after successful logout
                .and()
                .csrf().disable() // Disable CSRF protection
                .cors(); // Enable Cross-Origin Resource Sharing

        http.authenticationProvider(authenticationProvider()); // Set the custom authentication provider

        return http.build();
    }

    /**
     * Configures the global AuthenticationManager.
     *
     * @param auth           The AuthenticationManagerBuilder object.
     * @param passwordEncoder The PasswordEncoder used for encoding passwords.
     * @throws Exception if an error occurs while configuring the AuthenticationManager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder.encode("123")) // Set the encoded password for the user
                .roles("USER"); // Assign the "USER" role to the user
    }

    /**
     * Creates a DaoAuthenticationProvider bean.
     *
     * @return The DaoAuthenticationProvider bean.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(encoder);

        return authProvider;
    }

    /**
     * Creates an AuthenticationManager bean.
     *
     * @param authenticationConfiguration The AuthenticationConfiguration object.
     * @return The AuthenticationManager bean.
     * @throws Exception if an error occurs while creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

}