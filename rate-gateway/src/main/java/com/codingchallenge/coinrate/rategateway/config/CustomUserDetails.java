package com.codingchallenge.coinrate.rategateway.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * The custom user details for RatePageController.
 */
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    private String selectedCrypto;
    private String country;
    private String language;
    private String currency;
    private List<String> supportedCoins;

    public CustomUserDetails() {}

    public CustomUserDetails(String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, String selectedCrypto, String country, String language, String currency, List<String> supportedCoins) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.selectedCrypto = selectedCrypto;
        this.country = country;
        this.language = language;
        this.currency = currency;
        this.supportedCoins = supportedCoins;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Gets the selected cryptocurrency.
     *
     * @return The selected cryptocurrency.
     */
    public String getSelectedCrypto() {
        return selectedCrypto;
    }

    /**
     * Sets the selected cryptocurrency.
     *
     * @param selectedCrypto The selected cryptocurrency.
     */
    public void setSelectedCrypto(String selectedCrypto) {
        this.selectedCrypto = selectedCrypto;
    }

    /**
     * Gets the Country.
     *
     * @return The Country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the Country.
     *
     * @param country The Country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the Language.
     *
     * @return The Language.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the Language.
     *
     * @param language The Language.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the Currency.
     *
     * @return The Currency.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the Currency.
     *
     * @param currency The Currency.
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets the supported coins.
     *
     * @return The supported coins.
     */
    public List<String> getSupportedCoins() {
        return supportedCoins;
    }

    /**
     * Sets the supported coins.
     *
     * @param supportedCoins The supported coins.
     */
    public void setSupportedCoins(List<String> supportedCoins) {
        this.supportedCoins = supportedCoins;
    }

}
