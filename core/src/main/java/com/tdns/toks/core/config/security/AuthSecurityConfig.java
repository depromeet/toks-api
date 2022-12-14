package com.tdns.toks.core.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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

import com.tdns.toks.core.common.filter.ExceptionHandlerFilter;
import com.tdns.toks.core.common.filter.JwtAuthenticationFilter;
import com.tdns.toks.core.common.security.oauth.CustomOAuth2UserService;
import com.tdns.toks.core.common.security.oauth.OAuth2FailureHandler;
import com.tdns.toks.core.common.security.oauth.OAuth2SuccessHandler;
import com.tdns.toks.core.common.service.UserDetailService;
import com.tdns.toks.core.common.type.CORSType;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.permit-url}")
    private final String[] permitUrl;

    private final UserDetailService customUserDetailService;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())
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
                            response.setContentType("application/json");
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write(objectMapper.writeValueAsString(ApplicationErrorType.NO_AUTHORIZATION));
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

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
                "**/swagger-resources/**", "/configuration/security",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    // ??????????????? ????????? ?????? ??????
    @Bean
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }

    // was??? ????????? ?????? ???(session clustering)
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // userDetailService ?????????????????? ??????
        auth.userDetailsService(customUserDetailService);
    }

    // CORS Configuration
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(CORSType.CONFIGURATION.getAllowOrigins());
        configuration.setAllowedHeaders(CORSType.CONFIGURATION.getAllowHeaders());
        configuration.setAllowedMethods(CORSType.CONFIGURATION.getAllowMethods());
        configuration.setMaxAge(CORSType.CONFIGURATION.getMaxAge());
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
