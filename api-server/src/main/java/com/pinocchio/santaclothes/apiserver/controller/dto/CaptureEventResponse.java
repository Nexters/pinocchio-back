package com.pinocchio.santaclothes.apiserver.controller.dto;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@ApiModel(description = "캡쳐 이벤트 응답 결과")
@Value
@Builder
public class CaptureEventResponse {
	@ApiModelProperty(value = "이벤트 아이디", required = true)
	String eventId;

	@ApiModelProperty(value = "이미지 아이디", required = true)
	String imageId;

	@ApiModelProperty(value = "유저 아이디", required = true)
	String userId;

	@ApiModelProperty(dataType = "string", value = "이벤트 상태", example = "START, EXTRACT, REPORT, DONE", required = true)
	CaptureEventStatus status;

	@ApiModelProperty(value = "이벤트 결과")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable
	LabelResult result;
}
