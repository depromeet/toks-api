package com.tdns.toks.core.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonHeaderType {

    REFERER("referer"),
    ORIGIN("origin"),
    PRAGMA("pragma"),
    HOST("Host"),
    ACCEPT("accept"),
    SEC_FETCH_SITE("sec-fetch-site"),
    SEC_FETCH_DEST("sec-fetch-dest"),
    SEC_FETCH_MODE("sec-fetch-mode"),
    ACCEPT_LANGUAGE("accept-language"),
    CONTENT_TYPE("Content-Type"),
    AUTHORIZATION("Authorization"),
    CACHE_CONTROL("Cache-Control"),
    REQUESTED_WITH("X-Requested-With"),
    REQUEST_IP("X-Request-Id"),
    FORWARDED_FOR("X-Forwarded-For"),
    FORWARDED_PROTO("X-Forwarded-Proto"),
    FORWARDED_PORT("X-Forwarded-Port"),
    AMAZON_TRACE_ID("X-Amzn-Trace-Id"),
    ;

    private final String name;

}
