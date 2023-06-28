package com.codingchallenge.coinrate.rategateway.client.dto;

public class CurrencyLocaleDto {


    public CurrencyLocaleDto(){
    }

    public CurrencyLocaleDto(String countryCode, String langCode, String currencyCode, boolean defaultCurrency,
                             boolean localeNotFound) {

        this.countryCode = countryCode;
        this.langCode = langCode;
        this.currencyCode = currencyCode;
        this.defaultCurrency = defaultCurrency;
        this.isLocaleNotFound = localeNotFound;
    }

    private String countryCode;


    private String langCode;


    private String currencyCode;

    private boolean isLocaleNotFound;


    private boolean defaultCurrency;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public boolean isDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(boolean defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public boolean isLocaleNotFound() {
        return isLocaleNotFound;
    }

    public void setLocaleNotFound(boolean localeNotFound) {
        isLocaleNotFound = localeNotFound;
    }
}
