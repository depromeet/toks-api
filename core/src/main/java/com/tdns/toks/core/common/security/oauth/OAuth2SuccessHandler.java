package com.tdns.toks.core.common.security.oauth;

import com.tdns.toks.core.common.type.JwtToken;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.security.front-redirect-uri}")
    private String frontRedirectUri;

    @Value("${spring.security.oauth2.localhost}")
    private String local;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        var userDetailDTO = (UserDetailDTO) authentication.getPrincipal();

        String host = "https://tokstudy.com";

        String referer = request.getHeader("Referer");
        if (referer != null && referer.contains("localhost")) {
            host = local;
        }

        var url = setRedirectUrl(host + frontRedirectUri, userDetailDTO.getJwtToken());
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String setRedirectUrl(String redirectURL, JwtToken jwtToken) {
        var accessToken = jwtToken.getAccessToken();
        var refreshToken = jwtToken.getRefreshToken();
        return UriComponentsBuilder.fromUriString(redirectURL)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();
    }
}
