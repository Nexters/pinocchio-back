package com.pinocchio.santaclothes.apiserver.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinocchio.santaclothes.apiserver.type.LoginType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
public class RegisterRequest {
	@ApiModelProperty(value = "사용자 소셜 로그인 식별 번호", required = true)
	String socialId;

	@ApiModelProperty(value = "로그인 타입", example = "KAKAO", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	LoginType loginType;

	@ApiModelProperty(value = "닉네임", required = true)
	String nickName;
}