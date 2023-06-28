package com.codingchallenge.coinrate.rategateway.service.util;

import org.apache.http.conn.util.InetAddressUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Utility class for IP address operations.
 */
public class IpAddressUtil {

    private static final Logger logger = LogManager.getLogger(IpAddressUtil.class);

    /**
     * Checks if the provided IP address is valid.
     *
     * @param ipAddress The IP address to check.
     * @return {@code true} if the IP address is valid, {@code false} otherwise.
     */
    public static boolean isValidIpAddress(String ipAddress) {

        // Check if the IP address is null or empty
        if(ipAddress == null || ipAddress.isEmpty()) {
            return false;
        }

        // Check if the IP address is a valid IPv4 or IPv6 address using InetAddressUtils
        if(!InetAddressUtils.isIPv4Address(ipAddress) && !InetAddressUtils.isIPv6Address(ipAddress)) {
            return false;
        }

        try {
            // Attempt to resolve the IP address
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            return inetAddress instanceof java.net.Inet4Address || inetAddress instanceof java.net.Inet6Address;
        } catch (UnknownHostException e) {

            logger.info("Can not resolve IP address: " + ipAddress, e);
            // An exception occurred while resolving the IP address, indicating that it is invalid
            return false;
        }
    }
}
