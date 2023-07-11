package com.tdns.toks.api.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
    public static String getClientIp(HttpServletRequest request) {
        var ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
