package com.codingchallenge.coinrate.rategateway.web;

import com.codingchallenge.coinrate.rategateway.config.CustomUserDetails;
import com.codingchallenge.coinrate.rategateway.web.dto.RateFormDto;
import com.codingchallenge.coinrate.rategateway.web.dto.HistoryRateFormDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("RatePageController integration tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RatePageControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder encoder;

    private CustomUserDetails userDetails;

    @Value("${rate-gateway.default-country-code}")
    private String defaultCountryCode;

    @Value("${rate-gateway.default-lang-code}")
    private String defaultLangCode;

    @Value("${rate-gateway.default-currency-code}")
    private String defaultCurrencyCode;

    private static final String RATE_WITH_HISTORY_REST_API_ENDPOINT = "/rate-with-history";
    private static final String INDEX_REST_API_ENDPOINT = "/index";
    private static final String NOT_FOUND_REST_API_ENDPOINT = "/test-not-found";
    public static final String USERNAME = "user";
    public static final String PASSWORD = "123";

    @BeforeEach
    void setUp() {
        userDetails = new CustomUserDetails(USERNAME, encoder.encode(PASSWORD), true, true,
                true, true, null);
    }

    @Test
    @DisplayName("POST request without authentication returns redirect to login page")
    public void postNoAuthReturnsRedirectLogin() throws Exception {

        // Execute a POST request to the RATE_WITH_HISTORY_REST_API_ENDPOINT without authentication
        mockMvc.perform(post(RATE_WITH_HISTORY_REST_API_ENDPOINT))
                // Verify that the response is a redirect to the login page
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));

    }

    @Test
    @DisplayName("POST request with authentication returns redirect to index page")
    public void postAuthReturnsRedirectIndex() throws Exception {

        // Execute a POST request to the INDEX_REST_API_ENDPOINT with authentication
        mockMvc.perform(
                        post(INDEX_REST_API_ENDPOINT)
                                .contentType("application/x-www-form-urlencoded")
                                .with(user(userDetails)))
                // Verify that the response is a redirect to the index page
                .andExpect(MockMvcResultMatchers.redirectedUrl("/index"));

    }

    @Test
    @DisplayName("GET request with authentication for a non-existent REST API endpoint returns 404")
    public void getAuthNotFoundRestApiEndpoint() throws Exception {

        // Execute a GET request to a non-existent REST API endpoint with authentication
        mockMvc.perform(get(NOT_FOUND_REST_API_ENDPOINT).with(user(userDetails)))
                // Verify that the response status is 404 (Not Found)
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("POST request with authentication for rate with history should return new CoinRatesFormDTO")
    public void postAuthRateWithHistoryNewValue() throws Exception {

        RateFormDto formData = new RateFormDto();

        // Create locale and currency objects with default values
        Locale locale = new Locale(defaultLangCode, defaultCountryCode);
        Currency currency = Currency.getInstance(defaultCurrencyCode);

        // Convert the form data to JSON
        String formDataJson = objectMapper.writeValueAsString(formData);

        // Send POST request with authentication
        mockMvc.perform(post(RATE_WITH_HISTORY_REST_API_ENDPOINT)
                        .contentType("application/json").content(formDataJson).with(user(userDetails)))
                // Verify that the response status is OK (200)
                .andExpect(status().isOk())
                // Verify that the content type of the response is JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                // Verify response fields are not empty
                .andExpect(jsonPath("$.currentRate").isNotEmpty())
                .andExpect(jsonPath("$.updateTime").isNotEmpty())
                .andExpect(jsonPath("$.coins").isNotEmpty())
                .andExpect(jsonPath("$.historyRates").isNotEmpty())
                // Verify that the response fields are equal to the expected values
                .andExpect(jsonPath("$.country").value(locale.getDisplayCountry()))
                .andExpect(jsonPath("$.language").value(locale.getDisplayLanguage()))
                .andExpect(jsonPath("$.currency").value(currency.getDisplayName()));

    }

    @Test
    @DisplayName("POST request with authentication for rate with history should return current CoinRatesFormDTO")
    public void postAuthRateWithHistoryCurrentValue() throws Exception {

        RateFormDto formData = new RateFormDto();

        // Create locale and currency objects with default values
        Locale locale = new Locale(defaultLangCode, defaultCountryCode);
        Currency currency = Currency.getInstance(defaultCurrencyCode);

        // Set up the history rates with some initial values
        List<HistoryRateFormDto> historyRates = new ArrayList<>();
        historyRates.add(new HistoryRateFormDto(""+LocalDate.now().minusDays(1), "1.01"));
        historyRates.add(new HistoryRateFormDto(""+LocalDate.now().minusDays(2), "1.02"));
        historyRates.add(new HistoryRateFormDto(""+LocalDate.now().minusDays(3), "1.03"));

        // Set up the form data with the required values
        formData.setSelectedCoin("bitcoin");
        //formData.setHistoryRates(historyRates);

        //!formData.setCountry(locale.getDisplayCountry());
        //!formData.setLanguage(locale.getDisplayLanguage());
        //!formData.setCurrency(currency.getDisplayName());

        // Convert the form data to JSON
        String formDataJson = objectMapper.writeValueAsString(formData);

        // Set the user details with the required values
        //userDetails.setSelectedCrypto("bitcoin");
        //userDetails.setCountry(locale.getDisplayCountry());
        //userDetails.setLanguage(locale.getDisplayLanguage());
        //userDetails.setCurrency(currency.getDisplayName());

        // Send POST request with authentication
        mockMvc.perform(post(RATE_WITH_HISTORY_REST_API_ENDPOINT)
                        .contentType("application/json").content(formDataJson).with(user(userDetails)))
                // Verify that the response status is OK (200)
                .andExpect(status().isOk())
                // Verify that the content type of the response is JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                // Verify response fields are not empty
                .andExpect(jsonPath("$.currentRate").isNotEmpty())
                .andExpect(jsonPath("$.updateTime").isNotEmpty())
                .andExpect(jsonPath("$.coins").isNotEmpty())
                // Verify if response fields are arrays
                .andExpect(jsonPath("$.coins").isArray())
                .andExpect(jsonPath("$.historyRates").isArray())
                // Verify that the response fields are equal to the expected values
                .andExpect(jsonPath("$.historyRates.length()").value(historyRates.size()))
                //.andExpect(jsonPath("$.historyRates[0].date").value(historyRates.get(0).getDate().toString()))
                .andExpect(jsonPath("$.historyRates[0].rate").value(historyRates.get(0).getDisplayRate()))
                //.andExpect(jsonPath("$.historyRates[1].date").value(historyRates.get(1).getDate().toString()))
                .andExpect(jsonPath("$.historyRates[1].rate").value(historyRates.get(1).getDisplayRate()))
                //.andExpect(jsonPath("$.historyRates[2].date").value(historyRates.get(2).getDate().toString()))
                .andExpect(jsonPath("$.historyRates[2].rate").value(historyRates.get(2).getDisplayRate()))
                .andExpect(jsonPath("$.country").value(locale.getDisplayCountry()))
                .andExpect(jsonPath("$.language").value(locale.getDisplayLanguage()))
                .andExpect(jsonPath("$.currency").value(currency.getDisplayName()));

    }

    @Test
    @DisplayName("GET request for index page should return CoinRatesFormDTO")
    public void getIndexPage() throws Exception {

        // Execute a GET request to the INDEX_REST_API_ENDPOINT with authentication
        mockMvc.perform(get(INDEX_REST_API_ENDPOINT).with(user(userDetails)))
                // Verify that the response status is OK (200)
                .andExpect(status().isOk())
                // Verify that the view name is "index"
                .andExpect(view().name("index"))
                // Verify that the model attribute "coinRatesData" is not null
                .andExpect(model().attribute("coinRatesData", notNullValue()))
                // Verify that the model attribute "coinRatesData" is an instance of CoinRatesFormDTO
                .andExpect(model().attribute("coinRatesData", instanceOf(RateFormDto.class)));

    }


}