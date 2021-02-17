package com.pinocchio.santaclothes.apiserver.controller.dto;

import java.time.Instant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@ApiModel(description = "로그인 응답 결과")
@Value
@Builder
public class AuthResponse {
	@ApiModelProperty(value = "인증 토큰", required = true)
	String accessToken;

	@ApiModelProperty(value = "리프레시 토큰", required = true)
	String refreshToken;

	@ApiModelProperty(value = "유저 아이디", required = true)
	String userId;

	@ApiModelProperty(value = "만료 날짜", required = true)
	Instant expireDateTime;
}
