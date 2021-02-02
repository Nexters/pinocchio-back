package com.pinocchio.santaclothes.apiserver.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.exception.TokenInvalidException;
import com.pinocchio.santaclothes.apiserver.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {
	private static final int TOKEN_PREFIX = 7; // BEARER
	private final UserService userService;

	@Override
	public boolean preHandle(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull Object handler
	) {
		String authorization = request.getHeader("Authorization");
		if (authorization != null) {
			String token = authorization.substring(TOKEN_PREFIX);
			if (userService.isActiveToken(token) && !userService.isExpired(token)) {
				return true;
			}
		}
		throw new TokenInvalidException(ExceptionReason.INVALID_ACCESS_TOKEN);
	}
}
