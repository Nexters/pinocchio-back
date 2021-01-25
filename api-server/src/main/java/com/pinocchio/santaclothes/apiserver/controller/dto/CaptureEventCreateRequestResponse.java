package com.pinocchio.santaclothes.apiserver.controller.dto;

import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

public class CaptureEventCreateRequestResponse {
	@ApiModel(description = "캡쳐 이벤트 생성 요청")
	@Value
	@Builder
	public static class CaptureEventCreateRequest {
		@ApiModelProperty(value="이벤트 아이디", required = true)
		String eventId;

		@ApiModelProperty(value="이미지 아이디", required = true)
		String imageId;

		@ApiModelProperty(value="이벤트 상태", required = true)
		@Builder.Default
		CaptureEventStatus eventStatus = CaptureEventStatus.START;
	}

	@ApiModel(description = "캡쳐 이벤트 생성 응답 결과")
	@Value
	public static class CaptureEventCreateResponse{
		@ApiModelProperty(value="이벤트 아이디", required = true)
		String eventId;
	}
}
