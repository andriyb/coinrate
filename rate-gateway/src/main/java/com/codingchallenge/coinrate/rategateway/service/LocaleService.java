package com.codingchallenge.coinrate.rategateway.service;

import com.codingchallenge.coinrate.rategateway.client.LocaleServiceClient;
import com.codingchallenge.coinrate.rategateway.client.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.LocaleFormDto;
import com.codingchallenge.coinrate.rategateway.service.mapper.LocaleFormDtoMapper;
import com.codingchallenge.coinrate.rategateway.service.util.IpAddressUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

/**
 * Service class for retrieving the default currency locale based on configured default codes and client's IP address.
 */
@Service
public class LocaleService {

    @Value("${rate-gateway.default-country-code}")
    private String defaultCountryCode = "DE";

    @Value("${rate-gateway.default-lang-code}")
    private String defaultLangCode = "de";

    @Value("${rate-gateway.default-currency-code}")
    private String defaultCurrencyCode = "EUR";

    private static final Logger logger = LogManager.getLogger(LocaleService.class);

    final LocaleServiceClient localeServiceClient;

    @Autowired
    public LocaleService(LocaleServiceClient localeServiceClient) {

        this.localeServiceClient = localeServiceClient;
    }

    /**
     * Retrieves the default currency locale based on the configured default codes and the client's IP address.
     *
     * @param ipAddress The client's IP address.
     * @return The default LocaleFormDto representing the default currency locale.
     */
    public LocaleFormDto getDefaultCurrencyLocale(String ipAddress) {
        // Create the default LocaleFormDto based on the configured default codes
        LocaleFormDto defaultLocaleForm = LocaleFormDtoMapper.mapClientToFormDto(
                new CurrencyLocaleDto(defaultCountryCode, defaultLangCode, defaultCurrencyCode,
                        true, true), new Locale(defaultLangCode, defaultCountryCode));

        if (IpAddressUtil.isValidIpAddress(ipAddress)) {
            // Retrieve the list of currency locales from the locale service client
            ResponseEntity<List<CurrencyLocaleDto>> responseEntity =
                    localeServiceClient.getCurrencyLocales(ipAddress);

            if (responseEntity != null &&
                    HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                List<CurrencyLocaleDto> currencyLocaleList = responseEntity.getBody();

                if (currencyLocaleList != null && !currencyLocaleList.isEmpty()) {
                    // Use the first currency locale from the list as the default currency locale
                    CurrencyLocaleDto defaultCurrencyLocale = currencyLocaleList.get(0);
                    Locale locale = new Locale(defaultCurrencyLocale.getLangCode().toLowerCase(),
                            defaultCurrencyLocale.getCountryCode().toUpperCase());
                    // Map the default currency locale to the LocaleFormDto
                    return LocaleFormDtoMapper.mapClientToFormDto(defaultCurrencyLocale, locale);
                } else {
                    logger.info("CurrencyLocaleList from localeServiceClient.getCurrencyLocales(ipAddress) is " +
                                    "null or empty for Ip address:" + ipAddress);
                }
            } else {
                logger.info("ResponseEntity from localeServiceClient.getCurrencyLocales(ipAddress) is null " +
                        "or not OK for Ip address:" + ipAddress);
            }
        } else {
            logger.info("Ip address is not valid: " + ipAddress);
        }

        // Return the default LocaleFormDto if no currency locales were retrieved or an error occurred
        return defaultLocaleForm;
    }
}
