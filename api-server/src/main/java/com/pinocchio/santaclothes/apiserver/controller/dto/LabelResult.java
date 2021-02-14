package com.pinocchio.santaclothes.apiserver.controller.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pinocchio.santaclothes.apiserver.entity.type.BleachType;
import com.pinocchio.santaclothes.apiserver.entity.type.DryCleaning;
import com.pinocchio.santaclothes.apiserver.entity.type.DryType;
import com.pinocchio.santaclothes.apiserver.entity.type.IroningType;
import com.pinocchio.santaclothes.apiserver.entity.type.WaterType;
import com.pinocchio.santaclothes.apiserver.entity.type.ClothesColor;
import com.pinocchio.santaclothes.common.type.IngredientType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@ApiModel(description = "라벨 인식 결과")
@Value
@Builder
public class LabelResult {
	@ApiModelProperty(value = "원단 리스트", required = true)
	@NotEmpty
	@Builder.Default
	List<IngredientType> ingredientList = new ArrayList<>();

	@ApiModelProperty(value = "물 세탁 방법", required = true)
	@NotNull
	WaterType waterType;

	@ApiModelProperty(value = "세제 세탁 방법", required = true)
	@NotNull
	BleachType bleachType;

	@ApiModelProperty(value = "다림질 방법", required = true)
	@NotNull
	IroningType ironingType;

	@ApiModelProperty(value = "건조 방법", required = true)
	@NotNull
	DryType dryType;

	@ApiModelProperty(value = "드라이 클리닝 방법", required = true)
	@NotNull
	DryCleaning dryCleaning;

	@ApiModelProperty(value = "옷 색깔")
	ClothesColor clothesColor;
}
