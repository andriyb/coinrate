package com.codingchallenge.coinrate.rategateway.web;

import com.codingchallenge.coinrate.rategateway.config.CustomUserDetails;
import com.codingchallenge.coinrate.rategateway.service.CoinRateService;
import com.codingchallenge.coinrate.rategateway.service.LocaleService;
import com.codingchallenge.coinrate.rategateway.service.dto.form.*;
import com.codingchallenge.coinrate.rategateway.web.dto.request.ChangeCoinRequestDto;
import com.codingchallenge.coinrate.rategateway.web.dto.request.ChangeIpRequestDto;
import com.codingchallenge.coinrate.rategateway.web.dto.request.UpdateCurrentRateRequestDto;
import com.codingchallenge.coinrate.rategateway.web.dto.response.ChangeCoinResponseDto;
import com.codingchallenge.coinrate.rategateway.web.dto.response.ChangeIpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

/**
 * Controller class for handling cryptocurrency rates form requests.
 */
@Controller
public class CoinRateController {

    /**
     * The CoinRateService used for retrieving cryptocurrency rate-related data.
     */
    public final CoinRateService coinRateService;

    /**
     * The LocaleService used for retrieving user locale-related data.
     */
    public final LocaleService localeService;

    /**
     * The AuthenticationManager used for spring security based user authentication.
     */
    public final AuthenticationManager authenticationManager;

    /**
     * Constructs a new RatePageController with the given RateService and AuthenticationManager.
     *
     * @param coinRateService           The RateService used for retrieving currency rate-related data.
     * @param authenticationManager The AuthenticationManager used for user authentication.
     */
    @Autowired
    public CoinRateController(CoinRateService coinRateService,
                              LocaleService localeService, AuthenticationManager authenticationManager) {

        this.coinRateService = coinRateService;
        this.localeService = localeService;
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

        model.addAttribute("pageForm", createPageForm(request));
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

        ChangeCoinResponseDto responseDto = createChangeCoinResponse(changeCoinRequestDto.getCoinCode(),
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

        ChangeIpResponseDto responseDto = createChangeIpResponse(changeIpRequestDto.getCoinCode(),
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
                createUpdateCurrentRateResponse(currentRateRequestDto.getCoinCode());

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
                createHistoryRateFormList(changeCoinRequestDto.getCoinCode(), changeCoinRequestDto.getDaysCount());

        return ResponseEntity.ok(responseDtoList);
    }

    /**
     * Parses the IP address from the HttpServletRequest object.
     *
     * @param request The HttpServletRequest object containing the request information.
     * @return The parsed IP address.
     */
    protected String parseRequestIpAddress(HttpServletRequest request) {

        // Retrieve the IP address from the "X-FORWARDED-FOR" header
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        // If the "X-FORWARDED-FOR" header is not present or empty, retrieve the IP address from the remote address
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        } else {

            // Extract the first IP address from the "X-FORWARDED-FOR" header (if multiple addresses are present)
            int commaIndex = ipAddress.indexOf(",");
            if (commaIndex != -1) {
                ipAddress = ipAddress.substring(0, commaIndex).trim();
            }
        }

        // Return the parsed IP address
        return ipAddress;
    }

    /**
     * Saves the user's currency locale settings to the session.
     *
     * @param localeFormDto The CurrencyLocaleDto object representing the user's currency locale.
     */
    private void saveUserLocaleToSession(LocaleFormDto localeFormDto) {

        // Retrieve the currently authenticated principal from the security context
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Cast the principal to CustomUserDetails, assuming it represents the authenticated user's details
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        userDetails.setLocaleForm(localeFormDto);
    }

    /**
     * Loads the user's currency locale settings from the session.
     *
     * @return The LocaleFormDto object representing the user's  currency locale.
     */
    private LocaleFormDto loadUserLocaleFromSession() {

        // Retrieve the currently authenticated principal from the security context
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Cast the principal to CustomUserDetails, assuming it represents the authenticated user's details
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        return userDetails.getLocaleForm();
    }

    /**
     * Creates a ChangeCoinResponseDto object with the current rate and history rates for the specified
     * coin and duration.
     *
     * @param coinCode   The code representing the selected cryptocurrency.
     * @param daysCount  The number of days for which to retrieve the history rates.
     * @return The ChangeCoinResponseDto object containing the current rate and history rates.
     */
    private ChangeCoinResponseDto createChangeCoinResponse(String coinCode, Integer daysCount) {

        // Retrieve the user's selected currency locale from the session
        LocaleFormDto currencyLocale = loadUserLocaleFromSession();

        // Create a Locale object based on the currency locale information
        Locale locale = new Locale(currencyLocale.getLangCode(), currencyLocale.getCountryCode());

        // Get the current rate for the selected cryptocurrency and currency code
        CurrentRateFormDto currentRateForm =
                coinRateService.getCurrentRate(coinCode, currencyLocale.getCurrencyCode(), locale);

        // Get the history rates for the selected cryptocurrency and currency code
        List<HistoryRateFormDto> historyRatesFormList =
                coinRateService.getRateHistory(coinCode, currencyLocale.getCurrencyCode(), daysCount, locale);

        // Create and return the ChangeCoinResponseDto object with the current rate and history rates
        return new ChangeCoinResponseDto(currentRateForm, historyRatesFormList);
    }

    /**
     * Creates a ChangeIpResponseDto object with the current rate, history rates, and updated currency locale
     * loaded from the session for the specified coin, duration, and IP address.
     *
     * @param coinCode   The code representing the selected cryptocurrency.
     * @param daysCount  The number of days for which to retrieve the history rates.
     * @param ipAddress  The IP address used to determine the default currency locale.
     * @return The ChangeIpResponseDto object containing the current rate, history rates, and updated currency locale.
     */
    private ChangeIpResponseDto createChangeIpResponse(String coinCode, Integer daysCount, String ipAddress) {

        // Retrieve the default currency locale based on the IP address
        LocaleFormDto currencyLocale = localeService.getDefaultCurrencyLocale(ipAddress);

        // Update the user's currency locale in the session
        saveUserLocaleToSession(currencyLocale);

        // Create a Locale object based on the updated currency locale information
        Locale locale = new Locale(currencyLocale.getLangCode(), currencyLocale.getCountryCode());

        // Get the current rate for the selected cryptocurrency and currency code
        CurrentRateFormDto currentRateForm =
                coinRateService.getCurrentRate(coinCode, currencyLocale.getCurrencyCode(), locale);

        // Get the history rates for the selected cryptocurrency and currency code
        List<HistoryRateFormDto> historyRatesFormList =
                coinRateService.getRateHistory(coinCode, currencyLocale.getCurrencyCode(), daysCount, locale);

        // Create and return the ChangeIpResponseDto object with the current rate, history rates, and updated
        // currency locale
        return new ChangeIpResponseDto(currentRateForm, historyRatesFormList, currencyLocale);
    }

    /**
     * Creates a CurrentRateFormDto object with the current rate for the specified coin and user's currency locale
     * loaded from the session.
     *
     * @param coinCode The code representing the selected cryptocurrency.
     * @return The CurrentRateFormDto object containing the current rate.
     */
    private CurrentRateFormDto createUpdateCurrentRateResponse(String coinCode) {

        // Retrieve the user's selected currency locale from the session
        LocaleFormDto currencyLocale = loadUserLocaleFromSession();

        // Create a Locale object based on the currency locale information
        Locale locale = new Locale(currencyLocale.getLangCode(), currencyLocale.getCountryCode());

        // Retrieve the current rate for the specified coin and user's currency locale
        return coinRateService.getCurrentRate(coinCode, currencyLocale.getCurrencyCode(), locale);
    }

    /**
     * Creates a list of HistoryRateFormDto objects containing the history rates for the specified coin and duration.
     *
     * @param coinCode   The code representing the selected cryptocurrency.
     * @param daysCount  The number of days for which to retrieve the history rates.
     * @return The list of HistoryRateFormDto objects containing the history rates.
     */
    private List<HistoryRateFormDto> createHistoryRateFormList(String coinCode, Integer daysCount) {

        // Retrieve the user's selected currency locale from the session
        LocaleFormDto currencyLocale = loadUserLocaleFromSession();

        // Create a Locale object based on the currency locale information
        Locale locale = new Locale(currencyLocale.getLangCode(), currencyLocale.getCountryCode());

        // Retrieve the history rates for the specified coin, user's currency locale, and duration
        return coinRateService.getRateHistory(coinCode, currencyLocale.getCurrencyCode(), daysCount, locale);
    }


    /**
     * Creates a RateFormDto object with initial data for the new form rendered on the server-side.
     *
     * @param request The HttpServletRequest object used to retrieve the IP address.
     * @return The RateFormDto object containing the initial form data.
     */
    private CoinRateFormDto createPageForm(HttpServletRequest request) {


        // Retrieve the list of supported coins
        List<String> supportedCoinsList = coinRateService.getSupportedCoins();

        // Set the first coin in the supported coins list as the initially selected coin
        String selectedCoin = supportedCoinsList.get(0);

        // Parse the IP address from the request
        String ipAddress = parseRequestIpAddress(request);

        // Get the default currency locale information based on the user's IP address
        LocaleFormDto currencyLocale =  localeService.getDefaultCurrencyLocale(ipAddress);

        // Update the changed locale settings in the session
        saveUserLocaleToSession(currencyLocale);

        // Create a Locale object based on the currency locale information
        Locale locale = new Locale(currencyLocale.getLangCode().toLowerCase(),
                currencyLocale.getCountryCode().toUpperCase());

        // Retrieve the history settings from the rate service
        HistorySettingsFormDto historySettings = coinRateService.getHistorySettings();

        // Create and return the RateFormDto object with the initial form data
        return coinRateService.getFormRate(selectedCoin, currencyLocale.getCurrencyCode().toLowerCase(),
                 historySettings.getHistoryDaysCountDefault(), supportedCoinsList, ipAddress, currencyLocale,
                 historySettings, locale);
    }
}
