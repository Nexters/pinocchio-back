package com.pinocchio.santaclothes.apiserver.controller.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pinocchio.santaclothes.apiserver.domain.type.Bleach;
import com.pinocchio.santaclothes.apiserver.domain.type.Dry;
import com.pinocchio.santaclothes.apiserver.domain.type.DryCleaning;
import com.pinocchio.santaclothes.apiserver.domain.type.Ironing;
import com.pinocchio.santaclothes.apiserver.domain.type.Water;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel(description = "라벨 인식 결과")
@Value
public class LabelResultRequest {
	@ApiModelProperty(value = "원단 리스트", required = true)
	@NotEmpty
	List<Ingredient> ingredientList;

	@ApiModelProperty(value = "물 세탁 방법", required = true)
	@NotNull
	Water water;

	@ApiModelProperty(value = "세제 세탁 방법", required = true)
	@NotNull
	Bleach bleach;

	@ApiModelProperty(value = "다림질 방법", required = true)
	@NotNull
	Ironing ironing;

	@ApiModelProperty(value = "건조 방법", required = true)
	@NotNull
	Dry dry;

	@ApiModelProperty(value = "드라이 클리닝 방법", required = true)
	@NotNull
	DryCleaning dryCleaning;

}
