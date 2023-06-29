package com.codingchallenge.coinrate.rategateway.unit.util;

import com.codingchallenge.coinrate.rategateway.service.util.IpAddressUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IpAddressUtilTest {

    @Test
    @DisplayName("Should return currency locales from LocaleServiceClient when IP address is valid and known")
    void testValidIpAddress() {
        // Valid IPv4 address
        Assertions.assertTrue(IpAddressUtil.isValidIpAddress("192.168.0.1"));

        // Valid IPv6 address
        Assertions.assertTrue(IpAddressUtil.isValidIpAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));
    }

    @Test
    void testInvalidIpAddress() {
        // Empty IP address
        Assertions.assertFalse(IpAddressUtil.isValidIpAddress(""));

        // Null IP address
        Assertions.assertFalse(IpAddressUtil.isValidIpAddress(null));

        // Invalid IP address format
        Assertions.assertFalse(IpAddressUtil.isValidIpAddress("192.168.0"));

        // IP address that cannot be resolved
        Assertions.assertFalse(IpAddressUtil.isValidIpAddress("2001:0db8:7334"));
    }
}