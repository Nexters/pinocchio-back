package com.pinocchio.santaclothes.apiserver.controller.dto;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@ApiModel(description = "캡쳐 이벤트 업데이트 요청")
@Value
@Builder
public class CaptureEventUpdateRequest {
	@ApiModelProperty(value = "이미지 아이디", required = true)
	@NotNull
	String imageId;

	@ApiModelProperty(value = "이벤트 상태", allowableValues = "START, EXTRACT, REPORT, DONE", required = true)
	@NotNull
	CaptureEventStatus status;

	@ApiModelProperty(value = "분석 결과 JSON")
	@Nullable
	LabelResultRequest result;
}
