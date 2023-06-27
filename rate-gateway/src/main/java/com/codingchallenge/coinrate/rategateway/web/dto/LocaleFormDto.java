package com.codingchallenge.coinrate.rategateway.web.dto;
public class LocaleFormDto {

    private String country;
    private String language;
    private String currency;
    private boolean isDefault;

    private boolean isLocaleNotFound;


    public LocaleFormDto(String country, String language,
                         String currency, boolean aDefault, boolean localeNotFound) {

        this.country = country;
        this.language = language;
        this.currency = currency;
        this.isDefault = aDefault;
        this.isLocaleNotFound = localeNotFound;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isLocaleNotFound() {
        return isLocaleNotFound;
    }

    public void setLocaleNotFound(boolean localeNotFound) {
        isLocaleNotFound = localeNotFound;
    }
}
