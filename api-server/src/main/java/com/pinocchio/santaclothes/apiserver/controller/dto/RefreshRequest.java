package com.pinocchio.santaclothes.apiserver.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel(description = "토큰 리프레시 요청")
@Value
public class RefreshRequest {
	@ApiModelProperty(value = "리프레시 토큰", required = true)
	String refreshToken;
}
