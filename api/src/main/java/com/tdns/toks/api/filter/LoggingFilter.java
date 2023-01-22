package com.tdns.toks.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
public class LoggingFilter extends OncePerRequestFilter {

    // TODO : 로깅을 DB (no-sql 혹은 rdb에 저장)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);

        // TODO : 서비스 에러 우선적으로 확인하기 위해서 잠시 로깅을 주석처리
/*
		log.info("\n[Request] {} \n"
				+ "[Request Headers] {} \n"
				+ "[Request Body] {} \n"
				+ "[Status Code] {} \n"
				+ "[Response Body] {}",
			getRequest(request),
			getHeaders(request),
			contentBody(requestWrapper.getContentAsByteArray()),
			response.getStatus(),
			contentBody(responseWrapper.getContentAsByteArray())
		);
*/

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
            Map map = mapper.readValue(new String(contents), Map.class);
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (IOException e) {
            log.error("[JSON PARSE ERROR] {}", e.getMessage(), e);
        }
        return json;
    }
}
