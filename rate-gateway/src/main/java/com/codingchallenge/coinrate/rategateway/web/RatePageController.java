package com.codingchallenge.coinrate.rategateway.web;

import com.codingchallenge.coinrate.rategateway.config.CustomUserDetails;
import com.codingchallenge.coinrate.rategateway.service.RateService;
import com.codingchallenge.coinrate.rategateway.service.dto.*;
import com.codingchallenge.coinrate.rategateway.web.dto.*;
import com.codingchallenge.coinrate.rategateway.web.mapper.CurrentRateFormDtoMapper;
import com.codingchallenge.coinrate.rategateway.web.mapper.HistoryRateFormDtoMapper;
import com.codingchallenge.coinrate.rategateway.web.mapper.LocaleFormDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;


import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.IntStream;

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

        model.addAttribute("pageForm", createPageFormDto(request));
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
     * Handles the POST coin change request.
     *
     * @param changeCoinRequestDto The ChangeCoinRequestDto object containing form data.
     * @return A ResponseEntity containing the ChangeCoinResponseDto object.
     */
    @PostMapping(path = { "/change-coin", }, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> changeCoin(@RequestBody ChangeCoinRequestDto changeCoinRequestDto) {

        ChangeCoinResponseDto responseDto = createChangeCoinResponseDto(changeCoinRequestDto.getCoinCode(),
                changeCoinRequestDto.getDaysCount());
        return ResponseEntity.ok(responseDto);
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
     * Handles the POST IP change request.
     *
     * @param changeIpRequestDto The ChangeIpRequestDto object containing form data.
     * @return A ResponseEntity containing the ChangeCoinResponseDto object.
     */
    @PostMapping(path = { "/change-ip", }, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> changeIp(@RequestBody ChangeIpRequestDto changeIpRequestDto) {

        ChangeIpResponseDto responseDto = createChangeIpResponseDto(changeIpRequestDto.getCoinCode(),
                changeIpRequestDto.getDaysCount(), changeIpRequestDto.getIpAddress());
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Handles the POST update coin rate request.
     *
     * @param currentRateRequestDto The UpdateCurrentRateRequestDto object containing form data.
     * @return A ResponseEntity containing the UpdateCurrentRateResponseDto object.
     */
    @PostMapping(path = { "/update-current-rate", }, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> updateCurrentRate(@RequestBody UpdateCurrentRateRequestDto currentRateRequestDto) {

        CurrentRateFormDto currentRateForm =
                createUpdateCurrentRateResponseDto(currentRateRequestDto.getCoinCode());

        return ResponseEntity.ok(currentRateForm);
    }


    /**
     * Handles the POST update history rates request.
     *
     * @param changeCoinRequestDto The ChangeCoinRequestDto object containing form data.
     * @return A ResponseEntity containing the List of HistoryRateFormDto.
     */
    @PostMapping(path = { "/change-days-count", }, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> changeDaysCount(@RequestBody ChangeCoinRequestDto changeCoinRequestDto) {

        List<HistoryRateFormDto> responseDtoList =
                createHistoryRateFormDtoList(changeCoinRequestDto.getCoinCode(), changeCoinRequestDto.getDaysCount());

        return ResponseEntity.ok(responseDtoList);
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


    private void saveUserLocaleToSession(CurrencyLocaleDto currencyLocale) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        userDetails.setCurrencyLocale(currencyLocale);

    }

    private CurrencyLocaleDto loadUserLocaleFromSession() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        return userDetails.getCurrencyLocale();
    }


    private ChangeCoinResponseDto createChangeCoinResponseDto(String coinCode, Integer daysCount) {

        CurrencyLocaleDto currencyLocale = loadUserLocaleFromSession();
        Locale locale = new Locale(currencyLocale.getLangCode(), currencyLocale.getCountryCode());

        // Get the current rate for the selected cryptocurrency and currency code
        CurrentRateDto rate = rateService.getCurrentRate(coinCode, currencyLocale.getCurrencyCode());
        CurrentRateFormDto rateForm = CurrentRateFormDtoMapper.mapClientToFormDto(rate, locale);

        // Get the history rates for the selected cryptocurrency and currency code
        List<HistoryRateDto> historyRates = rateService.getRateHistory(coinCode,
                currencyLocale.getCurrencyCode(), daysCount);

        List<HistoryRateFormDto> historyRatesForm = HistoryRateFormDtoMapper.mapClientToFormDto(historyRates, locale);

        return new ChangeCoinResponseDto(rateForm, historyRatesForm);
    }

    private ChangeIpResponseDto createChangeIpResponseDto(String coinCode, Integer daysCount, String ipAddress) {

        // Get the currency locale information based on the user's IP address
        List<CurrencyLocaleDto> currencyLocaleDto = rateService.getLocales(ipAddress);
        // The first currency locale used as default currency locale for the country
        CurrencyLocaleDto currencyLocale = currencyLocaleDto.get(0);
        saveUserLocaleToSession(currencyLocale);

        Locale locale = new Locale(currencyLocale.getLangCode().toLowerCase(),
                currencyLocale.getCountryCode().toUpperCase());
        LocaleFormDto localeForm = LocaleFormDtoMapper.mapClientToFormDto(currencyLocale, locale);

        // Get the current rate for the selected cryptocurrency and currency code
        CurrentRateDto rate = rateService.getCurrentRate(coinCode, currencyLocale.getCurrencyCode());
        CurrentRateFormDto rateForm = CurrentRateFormDtoMapper.mapClientToFormDto(rate, locale);

        // Get the history rates for the selected cryptocurrency and currency code
        List<HistoryRateDto> historyRates = rateService.getRateHistory(coinCode,
                currencyLocale.getCurrencyCode(), daysCount);

        List<HistoryRateFormDto> historyRatesForm = HistoryRateFormDtoMapper.mapClientToFormDto(historyRates, locale);

        return new ChangeIpResponseDto(rateForm, historyRatesForm, localeForm);
    }

    private CurrentRateFormDto createUpdateCurrentRateResponseDto(
            String coinCode) {

        CurrencyLocaleDto currencyLocale = loadUserLocaleFromSession();
        Locale locale = new Locale(currencyLocale.getLangCode(), currencyLocale.getCountryCode());

        // Get the current rate for the selected cryptocurrency and currency code
        CurrentRateDto rate = rateService.getCurrentRate(coinCode, currencyLocale.getCurrencyCode());
        CurrentRateFormDto rateForm = CurrentRateFormDtoMapper.mapClientToFormDto(rate, locale);

        return rateForm;
    }


    private List<HistoryRateFormDto> createHistoryRateFormDtoList(
            String coinCode, Integer daysCount) {

        CurrencyLocaleDto currencyLocale = loadUserLocaleFromSession();
        Locale locale = new Locale(currencyLocale.getLangCode(), currencyLocale.getCountryCode());

        // Get the history rates for the selected cryptocurrency and currency code
        List<HistoryRateDto> historyRates = rateService.getRateHistory(coinCode,
                currencyLocale.getCurrencyCode(), daysCount);

        List<HistoryRateFormDto> historyRatesForm = HistoryRateFormDtoMapper.mapClientToFormDto(historyRates, locale);

        return historyRatesForm;
    }


    /**
     * Creates the RatePageFormDto object with initial data.
     *
     * @param request  The HttpServletRequest object to read IP address.
     * @return The RatePageFormDto object.
     */
    private RateFormDto createPageFormDto(HttpServletRequest request) {

        List<String> supportedCoinsList = rateService.getSupportedCoins();
        String selectedCoin = supportedCoinsList.get(0);
        String ipAddress = parseRequestIpAddress(request);

        // Get the currency locale information based on the user's IP address
        List<CurrencyLocaleDto> currencyLocaleDto = rateService.getLocales(ipAddress);
        // The first currency locale used as default currency locale for the country
        CurrencyLocaleDto currencyLocale = currencyLocaleDto.get(0);


        Locale locale = new Locale(currencyLocale.getLangCode().toLowerCase(),
                currencyLocale.getCountryCode().toUpperCase());
        LocaleFormDto localeForm = LocaleFormDtoMapper.mapClientToFormDto(currencyLocale, locale);

        HistorySettingsDto historySettings = rateService.getHistorySettings();

        FormRateDto formRate =  rateService.getFormRate(selectedCoin, currencyLocale.getCurrencyCode().toLowerCase(),
                historySettings.getHistoryDaysCountDefault());

        CurrentRateFormDto currentRateForm = CurrentRateFormDtoMapper.mapClientToFormDto(formRate, locale);

        List<HistoryRateFormDto> historyRatesFormList =
                HistoryRateFormDtoMapper.mapClientToFormDto(formRate.getHistory(), locale);


        saveUserLocaleToSession(currencyLocale);

        return new RateFormDto(supportedCoinsList, selectedCoin, ipAddress, localeForm, currentRateForm,
                historyRatesFormList, historySettings);
    }

}
