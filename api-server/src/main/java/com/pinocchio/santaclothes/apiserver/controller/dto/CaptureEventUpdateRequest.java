package com.pinocchio.santaclothes.apiserver.controller.dto;

import javax.validation.constraints.NotNull;

import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@ApiModel(description = "캡쳐 이벤트 업데이트 요청")
@Value
@Builder
public class CaptureEventUpdateRequest {
	@ApiModelProperty(value = "이벤트 아이디", required = true)
	@NotNull
	String eventId;

	@ApiModelProperty(value = "이미지 아이디", required = true)
	String imageId;

	@ApiModelProperty(value = "이벤트 상태", example = "KAKAO", required = true)
	@NotNull
	CaptureEventStatus status;
}
