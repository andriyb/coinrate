package com.codingchallenge.coinrate.localeservice.web.rest;

import com.codingchallenge.coinrate.localeservice.service.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.localeservice.service.CurrencyLocaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Currency Locale Microservice",
        description = "Microservice to Retrieve Locale Information by IP Address using the Free GeoLite2 Database.")
@RestController
@RequestMapping("${locale-service.api-path}${locale-service.api-version}")
public class CurrencyLocaleResource {
    private final CurrencyLocaleService currencyLocaleService;

    @Autowired
    public CurrencyLocaleResource(CurrencyLocaleService currencyLocaleService) {

        this.currencyLocaleService = currencyLocaleService;

    }

    @Operation(
            summary = "Creates Currency Locale information by IP address.",
            description = "Returns List of Currency Locales available for the Country detected by IP address.",
            tags = { "getCurrencyLocales", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CurrencyLocaleDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found") })
    @GetMapping("/getCurrencyLocales")
    public ResponseEntity<?> getCurrencyLocales(@RequestParam("ip") String ip) {
        return ResponseEntity.ok(currencyLocaleService.getCurrencyLocales(ip));
    }
}