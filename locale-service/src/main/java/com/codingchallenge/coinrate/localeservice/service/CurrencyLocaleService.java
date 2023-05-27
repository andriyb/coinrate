package com.codingchallenge.coinrate.localeservice.service;

import com.codingchallenge.coinrate.localeservice.domain.CurrencyLocale;
import com.codingchallenge.coinrate.localeservice.repository.CurrencyLocaleRepository;
import com.codingchallenge.coinrate.localeservice.service.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.localeservice.service.mapper.CurrencyLocaleDtoMapper;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing currency locales.
 */
@Service
public class CurrencyLocaleService {

    @Value("${locale-service.geolite-db-filename}")
    private String geoliteDbFileName;

    @Value("${locale-service.default-country-code}")
    private String defaultCountryCode;

    @Value("${locale-service.default-lang-code}")
    private String defaultLangCode;

    @Value("${locale-service.default-currency-code}")
    private String defaultCurrencyCode;

    private final ResourceLoader resourceLoader;

    private final CurrencyLocaleRepository currencyLocaleRepository;

    @Autowired
    public CurrencyLocaleService(CurrencyLocaleRepository currencyLocaleRepository, ResourceLoader resourceLoader) {

        this.currencyLocaleRepository = currencyLocaleRepository;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Retrieves the ISO country code based on the given IP address.
     *
     * @param ip The IP address.
     * @return The ISO country code.
     * @throws IOException      If an I/O error occurs while accessing the GeoLite database.
     * @throws GeoIp2Exception If an error occurs while retrieving the country information.
     */
    public String getISOCountryCode(String ip) throws IOException, GeoIp2Exception {

        // Load the GeoLite database from the classpath resource
        Resource resource = resourceLoader.getResource(geoliteDbFileName);
        DatabaseReader reader = new DatabaseReader.Builder(resource.getInputStream()).build();

        String isoCountryCode = "";
        try {
            // Get the InetAddress object for the given IP address
            InetAddress ipAddress = InetAddress.getByName(ip);
            // Retrieve the country information for the IP address from the GeoLite database
            CountryResponse countryResponse = reader.country(ipAddress);
            Country country = countryResponse.getCountry();
            // Get the ISO country code from the country information
            isoCountryCode = country.getIsoCode();
        } catch (Exception e) {
        }

        return isoCountryCode;
    }

    /**
     * Retrieves a list of CurrencyLocaleDto objects based on the given IP address.
     * If no currency locales are found, a default one is added.
     *
     * @param ip The IP address.
     * @return The list of CurrencyLocaleDto objects.
     */
    public List<CurrencyLocaleDto> getCurrencyLocales(String ip) {

        List<CurrencyLocale> currencyLocales = new ArrayList<>();
        List<CurrencyLocaleDto> currencyLocaleDtoList = new ArrayList<>();

        try {
            String code = getISOCountryCode(ip);
            currencyLocales.addAll(currencyLocaleRepository.getByCountryCode(code));


        CurrencyLocaleDtoMapper mapper = new CurrencyLocaleDtoMapper();
        currencyLocaleDtoList.addAll(currencyLocales.stream()
                .map(mapper::mapEntityToDto)
                .collect(Collectors.toList()));

        }catch (IOException | GeoIp2Exception e2) {
        }


        if (currencyLocaleDtoList.isEmpty()) {
            // If no currency locales are found, add a default one
            currencyLocaleDtoList.add(
                    new CurrencyLocaleDto(defaultCountryCode, defaultLangCode, defaultCurrencyCode, true));
        }

        return currencyLocaleDtoList;
    }
}
