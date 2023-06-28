package com.codingchallenge.coinrate.rategateway.client;

import com.codingchallenge.coinrate.rategateway.client.dto.CurrencyLocaleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The Locale Service API feign client.
 */
@FeignClient(name = "${microservices.localeService.serviceName}")
public interface LocaleServiceClient {

    /**
     * Retrieves a list of currency locales from LocaleService based on the provided IP address.
     *
     * @param ip The IP address.
     * @return The currency locale list.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "${microservices.localeService.urlPath}${microservices.localeService.currencyLocalesEndpoint}")
    ResponseEntity<List<CurrencyLocaleDto>> getCurrencyLocales(@RequestParam("ip") String ip);
}
