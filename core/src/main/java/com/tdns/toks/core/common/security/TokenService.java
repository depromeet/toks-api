package com.tdns.toks.core.common.security;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.auth.model.AuthToken;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.auth.model.AuthUserImpl;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.tdns.toks.core.common.security.Constants.TOKS_AUTH_HEADER_KEY;

@Component
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;

    private static String key;

    @Value("${spring.jwt.secret}")
    public void getSecretKey(String SECRET_KEY) {
        key = SECRET_KEY;
    }

    public String getAuthToken(HttpServletRequest request) {
        String accessToken = request.getHeader(TOKS_AUTH_HEADER_KEY); //인증토큰 값 가져오기

/*
        if (StringUtils.startsWithIgnoreCase(accessToken, AuthTokenType.BEARER_TYPE.getTokenType())) {
            return StringUtils.replaceIgnoreCase(accessToken, AuthTokenType.BEARER_TYPE.getTokenType(), "");
        }
*/

        if (StringUtils.isNotEmpty(accessToken)) {
            return accessToken;
        }

        return null;
    }

    public void verifyToken(AuthToken token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token.getToken());
//            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
            if (!claims.getBody().getExpiration().after(new Date())) { // 토큰 만료 시
                throw new SilentApplicationErrorException(ApplicationErrorType.EXPIRED_TOKEN);
            }
        } catch (Exception e) {
            throw new SilentApplicationErrorException(ApplicationErrorType.TOKEN_INTERNAL_ERROR);
        }
    }

/*    public static String getUserEmail(HttpServletRequest request) {
        String token = getAuthToken(request);
        verifyToken(token);
        return Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }*/


    public AuthUser getUserEmail(AuthToken token) {
        verifyToken(token);
        var email = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token.getToken()).getBody().getSubject();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));

        return new AuthUserImpl(user.getId(), user.getUserRole());
    }
}
