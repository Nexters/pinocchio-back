package com.pinocchio.santaclothes.apiserver.assembler.domain;

import java.util.List;

import com.pinocchio.santaclothes.apiserver.entity.type.BleachType;
import com.pinocchio.santaclothes.apiserver.entity.type.DryType;
import com.pinocchio.santaclothes.apiserver.entity.type.DryCleaning;
import com.pinocchio.santaclothes.apiserver.entity.type.IroningType;
import com.pinocchio.santaclothes.apiserver.entity.type.WaterType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@ApiModel(description = "결과 페이지 뷰")
@Value
@Builder
public class ResultView {
	@ApiModelProperty(value = "제목", required = true)
	String title;

	@ApiModelProperty(value = "설명", required = true)
	String description;

	@ApiModelProperty(value = "재질 리스트", required = true)
	List<IngredientView> ingredients;

	@ApiModelProperty(value = "세탁 라벨", required = true)
	WaterType waterType;

	@ApiModelProperty(value = "표백 라벨", required = true)
	BleachType bleachType;

	@ApiModelProperty(value = "건조 라벨", required = true)
	DryType dryType;

	@ApiModelProperty(value = "다림질 라벨", required = true)
	IroningType ironingType;

	@ApiModelProperty(value = "드라이 클리닝 라벨", required = true)
	DryCleaning dryCleaning;

	@ApiModel(description = "재질 뷰")
	@Value
	public static class IngredientView {
		@ApiModelProperty(value = "이름", required = true)
		String name;

		@ApiModelProperty(value = "함량 퍼센트", required = true)
		int percentage;
	}
}
