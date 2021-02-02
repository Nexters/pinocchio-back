package com.pinocchio.santaclothes.apiserver.controller.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel(description = "로그인 요청")
@Value
public class LoginRequest {
	@NotNull
	@ApiModelProperty(value = "사용자 소셜 로그인 식별 번호", required = true)
	String socialId;
}
