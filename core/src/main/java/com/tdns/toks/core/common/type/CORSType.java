package com.tdns.toks.core.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public enum CORSType {

    CONFIGURATION(
        // allowOrigins
        Collections.singletonList("*"),
        // allowHeaders
        Arrays.asList(
            CommonHeaderType.REFERER.getName(),
            CommonHeaderType.ORIGIN.getName(),
            CommonHeaderType.PRAGMA.getName(),
            CommonHeaderType.HOST.getName(),
            CommonHeaderType.ACCEPT.getName(),
            CommonHeaderType.SEC_FETCH_SITE.getName(),
            CommonHeaderType.SEC_FETCH_DEST.getName(),
            CommonHeaderType.SEC_FETCH_MODE.getName(),
            CommonHeaderType.ACCEPT_LANGUAGE.getName(),
            CommonHeaderType.CONTENT_TYPE.getName(),
            CommonHeaderType.AUTHORIZATION.getName(),
            CommonHeaderType.CACHE_CONTROL.getName(),
            CommonHeaderType.REQUESTED_WITH.getName(),
            CommonHeaderType.REQUEST_IP.getName(),
            CommonHeaderType.FORWARDED_FOR.getName(),
            CommonHeaderType.FORWARDED_PROTO.getName(),
            CommonHeaderType.FORWARDED_PORT.getName(),
            CommonHeaderType.AMAZON_TRACE_ID.getName()
        ),
        // allowMethods
        Arrays.asList(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.OPTIONS.name(),
            HttpMethod.HEAD.name(),
            HttpMethod.PATCH.name()
        ),
        // maxAge
        Duration.ofMinutes(10)
    )
    ;

    private final List<String> allowOrigins;
    private final List<String> allowHeaders;
    private final List<String> allowMethods;
    private final Duration maxAge;

}
