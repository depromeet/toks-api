package com.tdns.toks.api.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tdns.toks.api.filter.LoggingFilter;

@Configuration
public class ServletConfig {

	@Bean
	public FilterRegistrationBean<Filter> filterRegistrationBean() {
		FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new LoggingFilter());
		filter.addUrlPatterns("/*");
		return filter;
	}
}
