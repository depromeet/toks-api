package com.tdns.toks.core.common.security;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthToken;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.auth.model.AuthUserImpl;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
//    private final long accessTokenValidMillisecond = 1000L * 60 * 1; // AccessToken 1분 토큰 유효
    private final long accessTokenValidMillisecond = 1000L * 30 ; // AccessToken 30초 토큰 유효
    private final long refreshTokenValidMillisecond = 1000L * 60 * 1; // 1분 토큰 유효
//    private final long refreshTokenValidMillisecond = 1000L * 60 * 60 * 24 * 30; // 30일 토큰 유효
    private final UserRepository userRepository;

    private static String key;

    @Value("${spring.jwt.secret}")
    public void getSecretKey(String SECRET_KEY) {
        key = SECRET_KEY;
    }

    public String getAuthToken(HttpServletRequest request) {
        String accessToken = request.getHeader(TOKS_AUTH_HEADER_KEY); //인증토큰 값 가져오기
        if (StringUtils.isNotEmpty(accessToken)) {
            return accessToken;
        }
        return null;
    }

    public void verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token);
        } catch (Exception e) {
            if (e.getMessage().contains("JWT expired")) {
                throw new ApplicationErrorException(ApplicationErrorType.EXPIRED_TOKEN);
            }
            throw new ApplicationErrorException(ApplicationErrorType.TOKEN_INTERNAL_ERROR);
        }
        Long uid = getUserIdFromToken(token);
        if (!userRepository.existsById(uid)) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER);
        }
    }

    public AuthUser getAuthUser(AuthToken token) {
        verifyToken(token.getToken());
        var id = getUserIdFromToken(token.getToken());
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.UNKNOWN_USER));
        return new AuthUserImpl(id, user.getUserRole());
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf((Integer) Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody().get("uid"));
    }

    public String renewAccessToken(Long uid, String email) {
        return jwtBuilder(uid, email, accessTokenValidMillisecond);
    }

    private String jwtBuilder(Long id, String email, long expireTime) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("uid", id);
        Date now = new Date();
        return Jwts.builder().setClaims(claims)
                .setClaims(claims)
                .setIssuedAt(now)
                .setIssuer(String.valueOf(id))
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
    }
}
