package com.pinocchio.santaclothes.apiserver.config.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.exception.TokenInvalidException;
import com.pinocchio.santaclothes.apiserver.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {
	private static final int TOKEN_PREFIX = 7; // BEARER
	private final UserService userService;
	private static final List<String> permitHosts = List.of(
		"127.0.0.1",
		"ec2-3-139-60-119.us-east-2.compute.amazonaws.com"
	);

	@Override
	public boolean preHandle(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull Object handler
	) {
		if (permitHosts.contains(request.getRemoteHost())) {
			return true;
		}
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
