package com.codingchallenge.coinrate.rategateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class RateGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(RateGatewayApplication.class, args);
	}

}
