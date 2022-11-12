package com.tdns.toks.core.common.security;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
//        log.info("Redirecting to session expired page");
        HttpServletResponse response = event.getResponse();
        response.setStatus(ApplicationErrorType.INVALID_LOGIN_INFO.getHttpStatus().value());
        response.flushBuffer();
        throw new ApplicationErrorException(ApplicationErrorType.INVALID_LOGIN_INFO);
    }
}
