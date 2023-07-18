package com.tdns.toks.core.config.security;

import com.tdns.toks.core.common.filter.ExceptionHandlerFilter;
import com.tdns.toks.core.common.security.oauth.CustomOAuth2UserService;
import com.tdns.toks.core.common.security.oauth.OAuth2FailureHandler;
import com.tdns.toks.core.common.security.oauth.OAuth2SuccessHandler;
import com.tdns.toks.core.common.service.UserDetailService;
import com.tdns.toks.core.common.type.CORSType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.permit-url}")
    private final String[] permitUrl;

    private final UserDetailService customUserDetailService;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .cors().configurationSource(cors())
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(permitUrl).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    log.info("로그인 취소 및 오류");
                    response.sendRedirect("https://tokstudy.com");
//                    response.setContentType("application/json");
//                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    response.getWriter().write(objectMapper.writeValueAsString(ApplicationErrorType.NO_AUTHORIZATION));
                })
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FailureHandler);

        http.addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/v2/api-docs", "/configuration/ui",
                "**/swagger-resources/**", "/configuration/security",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/swagger-ui/index.html",

                // v2 API 시큐리티 적용 제외
                "/api/**"
        );
    }

    // 중복로그인 방지를 위한 로직
    @Bean
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }

    // was가 여러개 있을 때(session clustering)
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // userDetailService 커스터마이징 등록
        auth.userDetailsService(customUserDetailService);
    }

    // CORS Configuration
    @Bean
    protected CorsConfigurationSource cors() {
        CorsConfiguration configuration = new CorsConfiguration();
        var all = Collections.singletonList(CorsConfiguration.ALL);

        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://dev.tokstudy.com"));
        configuration.setAllowedHeaders(all);
        configuration.setAllowedMethods(all);
        configuration.setMaxAge(CORSType.CONFIGURATION.getMaxAge());
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
