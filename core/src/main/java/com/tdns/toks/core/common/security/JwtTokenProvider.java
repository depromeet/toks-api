package com.tdns.toks.core.common.security;

import com.tdns.toks.core.common.type.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

import static com.tdns.toks.core.common.security.Constants.TOKS_AUTH_HEADER_KEY;


/**
 * JWT토큰 생성 및 유효성을 검증하는 컴포넌트
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    /**
     * 사용자의 id와 email을 입력받아 AT, RT 쌍을 반환합니다.
     */
    public JwtToken generateTokenPair(Long id, String email) {
        return new JwtToken(
                // TODO : test를 위해, accessToken도 만료를 1년으로 변경, 추후 변경 필요
                jwtBuilder(id, email, TokenDuration.REFRESH_TOKEN_VALID_DURATION.duration),
                jwtBuilder(id, email, TokenDuration.REFRESH_TOKEN_VALID_DURATION.duration)
        );
    }

    public String renewAccessToken(Long id, String email) {
        return jwtBuilder(id, email, TokenDuration.ACCESS_TOKEN_VALID_DURATION.duration);
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public Long extractIdFromToken(String token) {
        return (Long) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token).getBody().get("uid");
    }

    public String getAuthToken(HttpServletRequest request) {
        var accessToken = request.getHeader(TOKS_AUTH_HEADER_KEY); //인증토큰 값 가져오기

        if (StringUtils.isNotEmpty(accessToken)) {
            return accessToken;
        }
        return null;
    }

    /**
     * 사용자의 id, email, 만료 시간을 인자로 토큰을 생성합니다.
     * 토큰의 payLoad 저장 값 = key :"uid" | value : 사용자의 id.
     * 토큰 발급자(issuer)와 Subject엔 Unique한 String값인 email을 등록합니다.
     *
     * @param id
     * @param email
     * @param expireTime
     * @return String 형태의 토큰을 반환합니다.
     */
    private String jwtBuilder(Long id, String email, long expireTime) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("uid", id);
        Date now = new Date();
        return Jwts.builder().setClaims(claims)
                .setClaims(claims)
                .setIssuedAt(now)
                .setIssuer(String.valueOf(id))
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @AllArgsConstructor
    enum TokenDuration {
        /**
         * AccessToken 1시간 토큰 유효
         */
//        ACCESS_TOKEN_VALID_DURATION(1000L * 60 * 60 * 1),
        ACCESS_TOKEN_VALID_DURATION(1000L * 30), // 30초 유효

        /**
         * 1달 토큰 유효
         */
//        REFRESH_TOKEN_VALID_DURATION(1000L * 60 * 60 * 24 * 30),
        REFRESH_TOKEN_VALID_DURATION(1000L * 60 * 1), // 1분 유효

        /**
         * 1년 토큰 유효
         */
        PERMENENT_TOKEN_VALID_DURATION(1000L * 60 * 60 * 24 * 365),
        ;

        private final Long duration;
    }
}
