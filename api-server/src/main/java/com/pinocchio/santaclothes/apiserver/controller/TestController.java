package com.pinocchio.santaclothes.apiserver.controller;

import java.time.Instant;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pinocchio.santaclothes.apiserver.entity.UserAuth;
import com.pinocchio.santaclothes.apiserver.repository.UserAuthRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Api(tags = "Test")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
	private final UserAuthRepository userAuthRepository;

	@ApiModel(description = "테스트 용 임시 인증 정보")
	@Value
	static class UserAuthRequestResponse {
		@ApiModelProperty(value = "유저 아이디", required = true)
		String userId;

		@ApiModelProperty(value = "인증 토큰", required = true)
		String accessToken;

		@ApiModelProperty(value = "리프레시 토큰", required = true)
		String refreshToken;

		@ApiModelProperty(value = "생성 날짜", required = true)
		Instant createdAt;

		@ApiModelProperty(value = "만료 날짜", required = true)
		Instant expiredAt;
	}

	@ApiOperation("테스트용 임시 인증 정보 생성")
	@PostMapping("/userAuth")
	public UserAuthRequestResponse userAuth(@RequestBody UserAuthRequestResponse request) {
		UserAuth auth = UserAuth.builder()
			.refreshToken(request.refreshToken)
			.accessToken(request.accessToken)
			.userId(request.userId)
			.expireDateTime(request.expiredAt)
			.createdDate(request.createdAt)
			.build();
		userAuthRepository.save(auth);

		return request;
	}
}
