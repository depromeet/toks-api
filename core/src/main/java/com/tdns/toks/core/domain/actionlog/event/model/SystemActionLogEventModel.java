package com.tdns.toks.core.domain.actionlog.event.model;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
public class SystemActionLogEventModel {
    private final String ipAddress;
    private final String method;
    private final String path;
    private final String userAgent;
    private final String host;
    private final String referer;

    public SystemActionLogEventModel(HttpServletRequest request) {
        this.ipAddress = getClientIpAddress(request);
        this.method = request.getMethod();
        this.path = request.getRequestURI();
        this.userAgent = request.getHeader("User-Agent");
        this.host = request.getHeader("Host");
        this.referer = request.getHeader("Referer");
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
