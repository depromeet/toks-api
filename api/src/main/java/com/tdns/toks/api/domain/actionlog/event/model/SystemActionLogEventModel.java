package com.tdns.toks.api.domain.actionlog.event.model;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

import static com.tdns.toks.core.common.utils.HttpUtil.getClientIpAddress;

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
}
