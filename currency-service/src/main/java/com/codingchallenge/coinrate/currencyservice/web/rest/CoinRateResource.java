package com.codingchallenge.coinrate.currencyservice.web.rest;

import java.util.List;

import com.codingchallenge.coinrate.currencyservice.client.data.CoinList;
import com.codingchallenge.coinrate.currencyservice.service.RateService;
import com.codingchallenge.coinrate.currencyservice.service.dto.FormRateDto;
import com.codingchallenge.coinrate.currencyservice.service.dto.HistoryRateDto;
import com.codingchallenge.coinrate.currencyservice.service.dto.RateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CoinRateResource {

    private final RateService rateService;

    @Autowired
    public CoinRateResource(RateService rateService) {

        this.rateService = rateService;
    }


    @Operation(
            summary = "Returns actual coin rate for specified currency.",
            description = "Returns actual coin rate for specified currency from the Coingecko API in json format.",
            tags = { "get-current-rate", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = RateDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404", description = "Not Found") })
    @GetMapping("/get-current-rate")
    public ResponseEntity<RateDto> getCurrentRate(@RequestParam(name = "coinCode") String coinCode,
                                         @RequestParam(name = "currencyCode") String currencyCode) {

        RateDto rateDto = rateService.getCurrentRate(coinCode, currencyCode);
        if (rateDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rateDto);
    }

    @Operation(
            summary = "Returns the history rates for the specified coin and currency.",
            description = "Returns the history rates for the specified oin and currency from the Coingecko API " +
                    "in json format.",
            tags = { "get-rate-history", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(
                    implementation = HistoryRateDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404", description = "Not Found") })
    @GetMapping("/get-rate-history")
    public ResponseEntity<List<HistoryRateDto>> getRateHistory(@RequestParam(name="coin-code") String coinCode,
                                               @RequestParam(name="currency-code") String currencyCode,
                                               @RequestParam(name="days-count",required=false) Integer daysCount) {

        return ResponseEntity.ok(rateService.getRateHistory(coinCode, currencyCode, daysCount));
    }

    @Operation(
            summary = "Returns the current rate and the history rates for the specified coin and currency.",
            description = "Returns rates history for the specified oin and currency from the Coingecko API " +
                    "in json format.",
            tags = { "get-form-rate", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = FormRateDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404", description = "Not Found") })
    @GetMapping("/get-form-rate")
    public ResponseEntity<FormRateDto> getFormRate(@RequestParam(name="coin-code") String coinCode,
                                   @RequestParam(name="currency-code") String currencyCode,
                                   @RequestParam(name="days-count",required=false) Integer daysCount) {

        return ResponseEntity.ok(rateService.getFormRate(coinCode, currencyCode, daysCount));
    }

    @Operation(
            summary = "Returns the list of supported coins.",
            description = "Returns the list of supported coins from the Coingecko API in json format.",
            tags = { "get-supported-coins", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CoinList.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404", description = "Not Found") })
    @GetMapping("/get-supported-coins")
    public ResponseEntity<List<CoinList>> getSupportedCoins() {

        return ResponseEntity.ok(rateService.getSupportedCoins());
    }

    @Operation(
            summary = "Load rates history into microservice database.",
            description = "Load rates history into microservice database from the Coingecko API " +
                    "with default settings.",
            tags = { "load-history", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PutMapping("/load-history")
    public ResponseEntity<?> loadHistory() {

        rateService.loadRatesHistory();
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete rates history from microservice database.",
            description = "Delete rates history from microservice database",
            tags = { "load-history", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @DeleteMapping("/delete-rate-history")
    @Transactional
    public ResponseEntity<?> deleteRateHistory() {

        rateService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
