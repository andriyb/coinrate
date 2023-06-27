package com.codingchallenge.coinrate.rategateway.web.mapper;

import com.codingchallenge.coinrate.rategateway.service.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.rategateway.web.dto.LocaleFormDto;

import java.util.Currency;
import java.util.Locale;

public class LocaleFormDtoMapper {
    public static LocaleFormDto mapClientToFormDto(CurrencyLocaleDto currencyLocaleDto, Locale currentLocale) {

        String currencyCode = currencyLocaleDto.getCurrencyCode().toUpperCase();

        // Get the display name of the country based on the ISO 3166 country code
        String countryDisplayName = currentLocale.getDisplayCountry();
        // Get the display name of the language based on the ISO 639 language code
        String langDisplayName = currentLocale.getDisplayLanguage();
        // Get the display name of the currency based on the ISO 4217 currency code
        String currencyDisplayName = Currency.getInstance(currencyCode).getDisplayName();

        return new LocaleFormDto(countryDisplayName, langDisplayName, currencyDisplayName,
                currencyLocaleDto.isDefaultCurrency(), currencyLocaleDto.isLocaleNotFound());
    }
}
