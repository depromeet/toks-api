package com.tdns.toks.core.common.utils;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.common.type.AuthTokenType;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.tdns.toks.core.common.security.Constants.TOKS_AUTH_HEADER_KEY;

@Component
public class TokenUtil {

    private static String key;

    @Value("${spring.jwt.secret}")
    public void getSecretKey(String SECRET_KEY) {
        key = SECRET_KEY;
    }

    public static String getAuthToken(HttpServletRequest request) {
        String accessToken = request.getHeader(TOKS_AUTH_HEADER_KEY); //인증토큰 값 가져오기

        if (StringUtils.startsWithIgnoreCase(accessToken, AuthTokenType.BEARER_TYPE.getTokenType())) {
            return StringUtils.replaceIgnoreCase(accessToken, AuthTokenType.BEARER_TYPE.getTokenType(), "");
        }

        if (StringUtils.isNotEmpty(accessToken)) {
            return accessToken;
        }
        return null;
    }

    public static void verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token);
//            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
            if (!claims.getBody().getExpiration().after(new Date())) { // 토큰 만료 시
                throw new SilentApplicationErrorException(ApplicationErrorType.EXPIRED_TOKEN);
            }
        } catch (Exception e) {
            throw new SilentApplicationErrorException(ApplicationErrorType.TOKEN_INTERNAL_ERROR);
        }
    }

    public static String getUserEmail(HttpServletRequest request) {
        String token = getAuthToken(request);
        verifyToken(token);
        return Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }
}
