package com.codingchallenge.coinrate.rategateway.web.dto;

public class LocaleDto {

    private String country;
    private String language;
    private String currency;

    public LocaleDto(String country, String language, String currency) {
        this.country = country;
        this.language = language;
        this.currency = currency;
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
}
