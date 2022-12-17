package com.tdns.toks.core.common.filter;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.common.security.JwtTokenProvider;
import com.tdns.toks.core.domain.user.model.dto.UserDTO;
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("#{'${security.filter-skip}' .split(',')}")
    private final List<String> filterSkipUrl;

    @Value("${security.permit-url}")
    private final String[] permitUrl;

    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰을 생성 및 검증 모듈 클래스
    private final UserService userService;

    // Jwt Provider 주입

    // Request로 들어오는 Jwt Token의 유효성을 검증 (jwtTokenProvider.validateToken)하는
    // filter를 filterChain에 등록한다.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.getAuthToken(request);

        // permitUrl에 포함되지 않고 토큰이 없는 경우 에러 발생
        if (!Arrays.stream(permitUrl).anyMatch(fl -> request.getServletPath().contains(fl)) && StringUtils.isEmpty(token)) {
            throw new ApplicationErrorException(ApplicationErrorType.INVALID_ACCESS_TOKEN);
        }

        if (token != null) {
            if (jwtTokenProvider.verifyToken(token)) {
                String email = jwtTokenProvider.getUid(token);
                User user = userService.getUserByStatus(email).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.UNAUTHORIZED_USER));
                Authentication auth = getAuthentication(UserDTO.convertEntityToDTO(user));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                throw new SilentApplicationErrorException(ApplicationErrorType.INVALID_LOGIN_INFO);
            }
        }
        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(UserDTO user) {
        return new UsernamePasswordAuthenticationToken(
                user,
                "",
                List.of(new SimpleGrantedAuthority(user.getUserRole().getAuthority()))
        );
    }

    @Override
    // Auth Filter 안태우는 URL 추가
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return filterSkipUrl.stream()
                .anyMatch(ex -> request.getServletPath().contains(ex));
    }
}

