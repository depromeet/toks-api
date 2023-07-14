package com.tdns.toks.core.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class HttpUtil {
    public static String getClientIpAddress(HttpServletRequest request) {
        var headers = new String[]{
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        var ipAddress = Arrays.stream(headers)
                .map(request::getHeader)
                .filter(ip -> ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip))
                .findFirst()
                .orElse(request.getRemoteAddr());

        try {
            var inetAddress = InetAddress.getByName(ipAddress);
            if (inetAddress.getHostAddress().contains(":")) {
                return inetAddress.getHostAddress().split(":")[0];
            } else {
                return inetAddress.getHostAddress();
            }
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
