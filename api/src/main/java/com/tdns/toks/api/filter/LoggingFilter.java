package com.tdns.toks.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdns.toks.api.domain.actionlog.event.model.SystemActionLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        applicationEventPublisher.publishEvent(new SystemActionLogEvent(request));

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);

        responseWrapper.copyBodyToResponse();
    }

    private String getRequest(HttpServletRequest request) {
        String result = "";
        result = request.getMethod() + " " + request.getRequestURI();
        result += request.getQueryString() != null ? "?" + request.getQueryString() : "";
        return result;
    }

    private String getHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();

        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(headerMap);
        } catch (IOException e) {
            log.error("[JSON PARSE ERROR] {}", e.getMessage(), e);
        }
        return json;
    }

    private String contentBody(final byte[] contents) {
        if (contents.length == 0) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            var map = mapper.readValue(new String(contents), Map.class);
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (IOException e) {
            log.error("[JSON PARSE ERROR] {}", e.getMessage(), e);
        }
        return json;
    }
}
