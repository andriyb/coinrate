package com.codingchallenge.coinrate.rategateway.service.dto.form;
public class LocaleFormDto {

    private String country;

    private String countryCode;
    private String language;

    private String langCode;
    private String currency;

    private String currencyCode;
    private boolean isDefault;

    private boolean isLocaleNotFound;

    public LocaleFormDto() {
    }

    public LocaleFormDto(String country, String countryCode, String language, String langCode, String currency,
                         String currencyCode, boolean isDefault, boolean isLocaleNotFound) {
        this.country = country;
        this.countryCode = countryCode;
        this.language = language;
        this.langCode = langCode;
        this.currency = currency;
        this.currencyCode = currencyCode;
        this.isDefault = isDefault;
        this.isLocaleNotFound = isLocaleNotFound;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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
