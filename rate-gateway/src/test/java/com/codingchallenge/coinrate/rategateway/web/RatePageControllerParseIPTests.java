package com.codingchallenge.coinrate.rategateway.web;

import com.codingchallenge.coinrate.rategateway.service.RateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("RatePageController parse request IP address tests")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RatePageControllerParseIPTests {

    @Mock
    private RateService rateService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private RatePageController ratePageController;

    public static final String IPV6_ADDRESS = "2001:0db8:85a3:0000:0000:8a2e:0370:7334";
    public static final String IPV4_ADDRESS = "129.144.52.38";
    public static final String IP_LOCAL_ADDRESS = "127.0.0.1";
    public static final String IPV4_ADDRESS_WITH_COMMA = "129.144.52.38, 127.0.0.1";

    @Test
    @DisplayName("Test parsing IPv6 address")
    void testParseRequestIpAddressIPv6() {

        // Create a new MockHttpServletRequest object and add an X-FORWARDED-FOR header with an IPv6 address
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-FORWARDED-FOR", IPV6_ADDRESS);

        // Parse the IP address from the request using the ratePageController's parseRequestIpAddress method
        String ipAddress = ratePageController.parseRequestIpAddress(request);

        // Verify that the parsed IP address is equal to the original IPv6 address
        Assertions.assertTrue(IPV6_ADDRESS.equals(ipAddress));

    }

    @Test
    @DisplayName("Test parsing IPv4 address")
    void testParseRequestIpAddressIPv4() {

        // Create a new MockHttpServletRequest object and add an X-FORWARDED-FOR header with an IPv4 address
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-FORWARDED-FOR", IPV4_ADDRESS);

        // Parse the IP address from the request using the ratePageController's parseRequestIpAddress method
        String ipAddress = ratePageController.parseRequestIpAddress(request);

        // Verify that the parsed IP address is equal to the original IPv4 address
        Assertions.assertTrue(IPV4_ADDRESS.equals(ipAddress));

    }

    @Test
    @DisplayName("Test parsing null header request: Expected result returns local IP address")
    void testParseRequestIpAddressNull() {

        // Create a new MockHttpServletRequest object without any headers
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Parse the IP address from the request using the ratePageController's parseRequestIpAddress method
        String ipAddress = ratePageController.parseRequestIpAddress(request);

        // Verify that the parsed IP address is equal to the local IP address
        Assertions.assertTrue(IP_LOCAL_ADDRESS.equals(ipAddress));

    }

    @Test
    @DisplayName("Test parsing IP address with comma: Expected result returns IP address before comma")
    void testParseRequestIpAddressWithComma() {

        // Create a new MockHttpServletRequest object and add an X-FORWARDED-FOR header with multiple IP addresses separated by a comma
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-FORWARDED-FOR", IPV4_ADDRESS_WITH_COMMA);

        // Parse the IP address from the request using the ratePageController's parseRequestIpAddress method
        String ipAddress = ratePageController.parseRequestIpAddress(request);

        // Verify that the parsed IP address is equal to the first IP address before the comma
        Assertions.assertTrue(IPV4_ADDRESS.equals(ipAddress));

    }

}