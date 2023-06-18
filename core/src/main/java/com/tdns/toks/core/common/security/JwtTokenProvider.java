package com.tdns.toks.core.common.security;

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

import static com.tdns.toks.core.common.security.Constants.TOKS_AUTH_HEADER_KEY;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {	// JWT토큰 생성 및 유효성을 검증하는 컴포넌트
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

//    private long tokenValidMillisecond = 1000L * 60 * 60; // 1시간 토큰 유효
    private long accesstokenValidMillisecond = 1000L * 60 * 60 * 24 * 120; // AccessToken 120일 토큰 유효
    private long refreshTokenValidMillisecond = 1000L * 60 * 60 * 24 * 30; // 30일 토큰 유효

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    /**
     * 사용자의 id와 email을 입력받아 AT, RT 쌍을 반환합니다.
     */
    public JwtToken generateTokenPair(Long id, String email) {
        return new JwtToken(
                jwtBuilder(id, email, accesstokenValidMillisecond),
                jwtBuilder(id, email, refreshTokenValidMillisecond)
        );
    }

    public String renewAccessToken(Long id, String email) {
        return jwtBuilder(id, email, accesstokenValidMillisecond);
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
        String accessToken = request.getHeader(TOKS_AUTH_HEADER_KEY); //인증토큰 값 가져오기

        /*if (StringUtils.startsWithIgnoreCase(accessToken, AuthTokenType.BEARER_TYPE.getTokenType())) {
            return StringUtils.replaceIgnoreCase(accessToken, AuthTokenType.BEARER_TYPE.getTokenType(), "");
        }*/

        if (StringUtils.isNotEmpty(accessToken)) {
            return accessToken;
        }
        return null;
    }

    /**
     * 사용자의 id, email, 만료 시간을 인자로 토큰을 생성합니다.
     * 토큰의 payLoad 저장 값 = key :"uid" | value : 사용자의 id.
     * 토큰 발급자(issuer)와 Subject엔 Unique한 String값인 email을 등록합니다.
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

}
