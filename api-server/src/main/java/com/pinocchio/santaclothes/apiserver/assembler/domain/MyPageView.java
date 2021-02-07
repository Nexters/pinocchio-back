package com.pinocchio.santaclothes.apiserver.assembler.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinocchio.santaclothes.common.type.ClothesType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@ApiModel(description = "마이 페이지 뷰")
@Value
@Builder
public class MyPageView {
	@ApiModelProperty(value = "나의 인식한 라벨 회수", required = true)
	@Builder.Default
	long myLabelCount = 0L;

	@ApiModelProperty(value = "카테고리 타입에 따른 인식한 옷 리스트", required = true)
	@Builder.Default
	Map<ClothesType, List<ResultView>> clothesByClothesType = new HashMap<>();
}
