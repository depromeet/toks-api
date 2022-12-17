package com.tdns.toks.api.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		filterChain.doFilter(requestWrapper, responseWrapper);

		log.info("[Request] {}", request.getMethod() + request.getRequestURI() + getQueryString(request));
		log.info("[Request Headers] {}", getHeaders(request));
		log.info("[Request Body] {}", contentBody(requestWrapper.getContentAsByteArray()));
		log.info("[Response Body] {}", contentBody(responseWrapper.getContentAsByteArray()));

		responseWrapper.copyBodyToResponse();
	}

	private Map<String, String> getHeaders(HttpServletRequest request) {
		Map<String, String> headerMap = new HashMap<>();

		Enumeration<String> headerArray = request.getHeaderNames();
		while (headerArray.hasMoreElements()) {
			String headerName = headerArray.nextElement();
			headerMap.put(headerName, request.getHeader(headerName));
		}
		return headerMap;
	}

	private String getQueryString(HttpServletRequest request) {
		return request.getQueryString() != null ? "?" + request.getQueryString() : "";
	}

	private String contentBody(final byte[] contents) {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			Map map = mapper.readValue(new String(contents), Map.class);
			json = mapper.writeValueAsString(map);
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
		} catch (IOException e) {
			log.error("[JSON PARSE ERROR] {}", e.getMessage(), e);
		}
		return json;
	}
}
