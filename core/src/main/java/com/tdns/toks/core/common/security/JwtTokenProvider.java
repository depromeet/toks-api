package com.tdns.toks.core.common.security;

import com.google.common.net.HttpHeaders;
import com.tdns.toks.core.common.type.AuthTokenType;
import com.tdns.toks.core.common.type.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {	// JWT토큰 생성 및 유효성을 검증하는 컴포넌트
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

//    private long tokenValidMillisecond = 1000L * 60 * 60; // 1시간 토큰 유효
    private long tokenValidMillisecond = 1000L * 60 * 60 * 24 * 120; // AccessToken 120일 토큰 유효
    private long refreshTokenValidMillisecond = 1000L * 60 * 60 * 24 * 30; // 30일 토큰 유효

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public JwtToken generateToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        return new JwtToken(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshTokenValidMillisecond))
                        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                        .compact());
    }

    public String renewAccessToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
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
    
    public String getUid(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public String getAuthToken(HttpServletRequest request) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION); //인증토큰 값 가져오기

        if (StringUtils.startsWithIgnoreCase(accessToken, AuthTokenType.BEARER_TYPE.getTokenType())) {
            return StringUtils.replaceIgnoreCase(accessToken, AuthTokenType.BEARER_TYPE.getTokenType(), "");
        }

        if (StringUtils.isNotEmpty(accessToken)) {
            return accessToken;
        }
        return null;
    }

}