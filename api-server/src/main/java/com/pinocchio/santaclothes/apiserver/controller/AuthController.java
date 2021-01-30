package com.pinocchio.santaclothes.apiserver.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pinocchio.santaclothes.apiserver.controller.dto.AuthResponse;
import com.pinocchio.santaclothes.apiserver.controller.dto.LoginRequest;
import com.pinocchio.santaclothes.apiserver.controller.dto.RefreshRequest;
import com.pinocchio.santaclothes.apiserver.controller.dto.RegisterRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Auth")
@RestController
@RequestMapping("/auth")
public class AuthController {
	@ApiOperation("회원가입")
	@ApiResponses(value = {
		@ApiResponse(code = 201, message = "회원가입 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류"),
		@ApiResponse(code = 409, message = "이미 존재하는 회원 입니다")
	})
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void register(RegisterRequest registerRequest) {
		// TODO: 회원가입, 중복시 409
	}

	@ApiOperation("리프레시 토큰 갱신")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "토큰 갱신 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 리프레시 토큰"),
	})
	@PutMapping("/token/refresh")
	public AuthResponse refresh(RefreshRequest request) {
		// TODO 리프레시 토큰 갱신 추가
		String refreshToken = UUID.randomUUID().toString();
		String authToken = UUID.randomUUID().toString();
		Instant expireDate = Instant.now().plus(1, ChronoUnit.MONTHS);

		return AuthResponse.builder()
			.refreshToken(refreshToken)
			.authToken(authToken)
			.expireDateTime(expireDate)
			.build();
	}

	@ApiOperation("로그인")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "로그인 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류"),
		@ApiResponse(code = 403, message = "인증 실패")
	})
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public AuthResponse login(LoginRequest loginRequest) {
		// TODO: 로그인 적용
		String refreshToken = UUID.randomUUID().toString();
		String authToken = UUID.randomUUID().toString();
		Instant expireDate = Instant.now().plus(30, ChronoUnit.DAYS);

		return AuthResponse.builder()
			.refreshToken(refreshToken)
			.authToken(authToken)
			.expireDateTime(expireDate)
			.build();
	}
}
