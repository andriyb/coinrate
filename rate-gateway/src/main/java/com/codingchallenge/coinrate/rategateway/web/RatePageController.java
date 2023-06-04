package com.codingchallenge.coinrate.rategateway.web;

import com.codingchallenge.coinrate.rategateway.config.CustomUserDetails;
import com.codingchallenge.coinrate.rategateway.service.RateService;
import com.codingchallenge.coinrate.rategateway.service.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.rategateway.service.dto.HistoryRateDto;
import com.codingchallenge.coinrate.rategateway.web.dto.CoinRatesFormDTO;
import com.codingchallenge.coinrate.rategateway.web.dto.CurrentRateDto;
import com.codingchallenge.coinrate.rategateway.web.dto.LocaleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import java.text.NumberFormat;
import java.util.*;

/**
 * Controller class for handling rate-related pages and requests.
 */
@Controller
public class RatePageController {

    public final RateService rateService;

    public final AuthenticationManager authenticationManager;

    @Autowired
    public RatePageController(RateService rateService, AuthenticationManager authenticationManager) {

        this.rateService = rateService;
        this.authenticationManager = authenticationManager;

    }

    /**
     * Handles the GET request for the index page.
     *
     * @param model     The Model object for passing data to the view.
     * @param request   The HttpServletRequest object for retrieving request information.
     * @return The name of the index view.
     */
    @GetMapping(path = { "/index", "/" })
    public String index(Model model, HttpServletRequest request) {

        CoinRatesFormDTO coinRatesData = loadAllFormData(new CoinRatesFormDTO());
        coinRatesData.setIpAddress(parseRequestIpAddress(request));

        model.addAttribute("coinRatesData", coinRatesData);

        return "index";
    }

    /**
     * Handles the POST request for the index page.
     *
     * @return The redirect URL to the index page.
     */
    @PostMapping(path = { "/index", "/" }, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String indexPost() {

        return "redirect:/index";
    }

    /**
     * Handles the POST request for retrieving rate with history.
     *
     * @param formData  The CoinRatesFormDTO object containing form data.
     * @return A ResponseEntity containing the CoinRatesFormDTO object.
     */
    @PostMapping(path = { "/rate-with-history", }, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getRateWithHistory(@RequestBody CoinRatesFormDTO formData) {

        CoinRatesFormDTO coinRatesData = loadAllFormData(formData);

        // Return the response with the appropriate status code
        return ResponseEntity.ok(coinRatesData);
    }

    /**
     * Handles the POST request for retrieving detected IP address.
     *
     * @return A ResponseEntity containing the CoinRatesFormDTO object.
     */
    @PostMapping(path = { "/detect-ip", })
    public ResponseEntity<Object> detectIp(HttpServletRequest request) {


        String ipAddress = parseRequestIpAddress(request);
        // Return the response with detected IP address and the appropriate status code
        return ResponseEntity.ok(ipAddress);
    }



    /**
     * Parses the IP address from the given HttpServletRequest object.
     *
     * @param request   The HttpServletRequest object.
     * @return The parsed IP address.
     */
    protected String parseRequestIpAddress(HttpServletRequest request) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        } else {
            // Extract the first IP address from the "X-FORWARDED-FOR" header
            int commaIndex = ipAddress.indexOf(",");
            if (commaIndex != -1) {
                ipAddress = ipAddress.substring(0, commaIndex).trim();
            }
        }

        return ipAddress;
    }

    /**
     * Loads the CoinRatesFormDTO object with initial data.
     *
     * @param formData  The CoinRatesFormDTO object to load data into.
     * @return The loaded CoinRatesFormDTO object.
     */
    protected CoinRatesFormDTO loadAllFormData(CoinRatesFormDTO formData) {

        Date updateTime = new Date();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principal;

        // Get the currency locale information based on the user's IP address
        List<CurrencyLocaleDto> currencyLocaleDto = rateService.getLocales(formData.getIpAddress());

        // Extract the language code, country code, and currency code from the currency locale information
        String langCode = currencyLocaleDto.get(0).getLangCode();
        String countryCode = currencyLocaleDto.get(0).getCountryCode();
        String currencyCode = currencyLocaleDto.get(0).getCurrencyCode();

        // Create a Locale object based on the extracted language code and country code
        Locale currentLocale = new Locale(langCode, countryCode);
        // Get the display name of the currency based on the currency code
        String currentCurrency = Currency.getInstance(currencyCode).getDisplayName();

        // Set the default cryptocurrency code to "bitcoin"
        String coinCode = "bitcoin";
        if(formData.getSelectedCoin() != null) {
            // If a cryptocurrency is selected in the form, update the coinCode
            coinCode = formData.getSelectedCoin();
        }

        // Get the current rate for the selected cryptocurrency and currency code
        BigDecimal currentRate = rateService.getCurrentRate(coinCode, currencyCode.toLowerCase()).getCurrentRate();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(currentLocale);
        String formattedRate = currencyFormat.format(currentRate);


        if (userDetails.getSelectedCrypto() == null) {

            //Get the list of supported coins
            List<String> supportedCoinsList = rateService.getSupportedCoins();

            // Set default values for userDetails if userDetails data has not been initialized
            userDetails.setSelectedCrypto(coinCode);
            userDetails.setCountry(currentLocale.getDisplayCountry());
            userDetails.setLanguage(currentLocale.getDisplayLanguage());
            userDetails.setCurrency(currentCurrency);
            userDetails.setSupportedCoins(supportedCoinsList);
        }

        // Check if any of the form data fields differ from the userDetails
        if (!userDetails.getSelectedCrypto().equals(formData.getSelectedCoin()) ||
            !userDetails.getCountry().equals(currentLocale.getDisplayCountry()) ||
            !userDetails.getLanguage().equals(currentLocale.getDisplayLanguage()) ||
            !userDetails.getCurrency().equals(currentCurrency)) {

            // Update userDetails if any of the form data fields differ from userDetails
            userDetails.setSelectedCrypto(coinCode);
            userDetails.setCountry(currentLocale.getDisplayCountry());
            userDetails.setLanguage(currentLocale.getDisplayLanguage());
            userDetails.setCurrency(currentCurrency);

            // Get the history rates for the selected cryptocurrency and currency code
            List<HistoryRateDto> historyRates = rateService.getRateHistory(userDetails.getSelectedCrypto(), currencyCode.toLowerCase(), 7);

            // Return CoinRatesFormDTO with updated values
            return new CoinRatesFormDTO(userDetails.getSupportedCoins(), "","", historyRates,
                    new CurrentRateDto(formattedRate, "" + updateTime),
                            new LocaleDto(currentLocale.getDisplayCountry(), currentLocale.getDisplayLanguage(), currentCurrency));

        } else {

            // Return CoinRatesFormDTO with the original form data
            return new CoinRatesFormDTO(userDetails.getSupportedCoins(),"", "", formData.getHistoryRates(),
                    new CurrentRateDto(formattedRate, "" + updateTime),
                    new LocaleDto(formData.getLocale().getLanguage(), formData.getLocale().getCountry(), formData.getLocale().getCurrency()));

        }

    }

}
