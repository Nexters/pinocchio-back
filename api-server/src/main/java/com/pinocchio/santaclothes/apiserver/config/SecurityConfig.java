package com.pinocchio.santaclothes.apiserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pinocchio.santaclothes.apiserver.config.interceptor.SecurityInterceptor;
import com.pinocchio.santaclothes.apiserver.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
	private final UserService userService;

	@Bean
	public SecurityInterceptor securityInterceptor() {
		return new SecurityInterceptor(userService);
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor()).addPathPatterns("/api/**");
	}
}
