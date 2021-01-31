package com.pinocchio.santaclothes.apiserver.controller;

import java.time.Instant;

import javax.validation.Valid;

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
import com.pinocchio.santaclothes.apiserver.domain.UserAuth;
import com.pinocchio.santaclothes.apiserver.exception.ProblemModel;
import com.pinocchio.santaclothes.apiserver.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(tags = "Auth")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final UserService userService;

	@ApiOperation("회원가입")
	@ApiResponses(value = {
		@ApiResponse(code = 201, message = "회원가입 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류", response = ProblemModel.class),
		@ApiResponse(code = 409, message = "이미 존재하는 회원 입니다", response = ProblemModel.class)
	})
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@Valid RegisterRequest registerRequest) {
		String socialId = registerRequest.getSocialId();
		String nickName = registerRequest.getNickName();
		userService.register(socialId, nickName);
	}

	@ApiOperation("인증 토큰 갱신")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "토큰 갱신 성공"),
		@ApiResponse(code = 404, message = "존재하지 않는 리프레시 토큰", response = ProblemModel.class),
	})
	@PutMapping("/authToken")
	public AuthResponse refresh(@Valid RefreshRequest request) {
		UserAuth userAuth = userService.refresh(request.getRefreshToken());
		String refreshToken = userAuth.getRefreshToken();
		String authToken = userAuth.getAuthToken();
		Instant expireDate = userAuth.getExpireDate();

		return AuthResponse.builder()
			.refreshToken(refreshToken)
			.authToken(authToken)
			.expireDateTime(expireDate)
			.build();
	}

	@ApiOperation("로그인")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "로그인 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류", response = ProblemModel.class),
		@ApiResponse(code = 403, message = "인증 토큰 만료", response = ProblemModel.class),
		@ApiResponse(code = 404, message = "존재하지 않는 소셜 아이디", response = ProblemModel.class),
	})
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public AuthResponse login(@Valid LoginRequest loginRequest) {
		UserAuth auth = userService.login(loginRequest.getSocialId());

		String refreshToken = auth.getRefreshToken();
		String authToken = auth.getAuthToken();
		Instant expireDate = auth.getExpireDate();

		return AuthResponse.builder()
			.refreshToken(refreshToken)
			.authToken(authToken)
			.expireDateTime(expireDate)
			.build();
	}
}
