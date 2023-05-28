package com.codingchallenge.coinrate.currencyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableCaching
@SpringBootApplication
public class CurrencyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyServiceApplication.class, args);
    }

}
