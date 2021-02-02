package com.pinocchio.santaclothes.apiserver.controller.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel(description = "원단 성분")
@Value
public class Ingredient {
	@ApiModelProperty(value = "원단 이름", required = true)
	@NotNull
	String name;

	@ApiModelProperty(value = "원단 함량 퍼센트", required = true)
	@NotNull
	int percentage;
}
