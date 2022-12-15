package com.tdns.toks.core.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (SilentApplicationErrorException ex){
            log.error("exception exception handler filter");
            ApplicationErrorType responseStatusType = ex.getResponseStatusType();
            setErrorResponseHeader(ex.getResponseStatusType().getHttpStatus(),response,ex);
            setErrorResponseBody(response, responseStatusType);
        }catch (RuntimeException ex){
            log.error("runtime exception exception handler filter");
            setErrorResponseHeader(HttpStatus.FORBIDDEN,response,ex);
        }
    }

    private void setErrorResponseHeader(HttpStatus status, HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
    }

    public void setErrorResponseBody(HttpServletResponse response, ApplicationErrorType applicationErrorType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String errorResponse = objectMapper.writeValueAsString(applicationErrorType);
        response.getWriter().write(errorResponse);
    }
}