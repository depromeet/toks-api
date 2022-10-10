package com.tdns.toks.core.config.security;

import com.tdns.toks.core.common.filter.ExceptionHandlerFilter;
import com.tdns.toks.core.common.filter.JwtAuthenticationFilter;
import com.tdns.toks.core.common.service.UserDetailService;
import com.tdns.toks.core.common.type.CORSType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.permit-url}")
    private final String[] permitUrl;

    private final UserDetailService customUserDetailService;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("*").permitAll();
//        http.httpBasic().disable().csrf().disable()
//                .formLogin().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(permitUrl).permitAll()
//                .antMatchers(HttpMethod.POST, "/**").authenticated()
//                .antMatchers(HttpMethod.DELETE, "/**").authenticated()
//                .antMatchers(HttpMethod.PUT, "/**").authenticated()
//                .antMatchers(HttpMethod.PATCH, "/**").authenticated()
//                .anyRequest().permitAll();

//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",  "/configuration/ui",
                "**/swagger-resources/**", "/configuration/security",
                "/swagger-ui.html", "/webjars/**","/swagger/**");
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
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(CORSType.CONFIGURATION.getAllowOrigins());
        configuration.setAllowedHeaders(CORSType.CONFIGURATION.getAllowHeaders());
        configuration.setAllowedMethods(CORSType.CONFIGURATION.getAllowMethods());
        configuration.setMaxAge(CORSType.CONFIGURATION.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
