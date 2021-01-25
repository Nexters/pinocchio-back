package com.pinocchio.santaclothes.apiserver.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinocchio.santaclothes.apiserver.type.LoginType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel(description = "로그인 요청")
@Value
public class LoginRequest {
	@ApiModelProperty(value = "유저 토큰", required = true)
	String userToken;

	@ApiModelProperty(value = "로그인 타입", example = "KAKAO", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	LoginType loginType;
}
