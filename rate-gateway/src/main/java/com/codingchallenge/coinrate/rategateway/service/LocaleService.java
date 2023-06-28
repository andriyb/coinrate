package com.codingchallenge.coinrate.rategateway.service;

import com.codingchallenge.coinrate.rategateway.client.LocaleServiceClient;
import com.codingchallenge.coinrate.rategateway.client.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.LocaleFormDto;
import com.codingchallenge.coinrate.rategateway.service.util.IpAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static com.codingchallenge.coinrate.rategateway.service.mapper.LocaleFormDtoMapper.mapClientToFormDto;

@Service
public class LocaleService {

    @Value("${rate-gateway.default-country-code}")
    private String defaultCountryCode = "DE";

    @Value("${rate-gateway.default-lang-code}")
    private String defaultLangCode = "de";

    @Value("${rate-gateway.default-currency-code}")
    private String defaultCurrencyCode = "EUR";

    final LocaleServiceClient localeServiceClient;

    @Autowired
    public LocaleService(LocaleServiceClient localeServiceClient) {

        this.localeServiceClient = localeServiceClient;
    }

    public LocaleFormDto getDefaultCurrencyLocale(String ipAddress) {

        LocaleFormDto defaultLocaleForm = mapClientToFormDto(
                new CurrencyLocaleDto(defaultCountryCode, defaultLangCode, defaultCurrencyCode,
                        true, true), new Locale(defaultLangCode, defaultCountryCode));

        if(IpAddressUtil.isValidIpAddress(ipAddress)) {

            // Retrieve the list of currency locales from the locale service client
            ResponseEntity<List<CurrencyLocaleDto>> responseEntity =
                    localeServiceClient.getCurrencyLocales(ipAddress);

            if (responseEntity != null &&
                    HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                List<CurrencyLocaleDto> currencyLocaleList = responseEntity.getBody();

                if (currencyLocaleList != null && !currencyLocaleList.isEmpty()) {
                    CurrencyLocaleDto defaultCurrencyLocale = currencyLocaleList.get(0);
                    Locale locale = new Locale(defaultCurrencyLocale.getLangCode().toLowerCase(),
                            defaultCurrencyLocale.getCountryCode().toUpperCase());
                    return mapClientToFormDto(defaultCurrencyLocale, locale);
                }
            }
        }

        return defaultLocaleForm;
    }
}
