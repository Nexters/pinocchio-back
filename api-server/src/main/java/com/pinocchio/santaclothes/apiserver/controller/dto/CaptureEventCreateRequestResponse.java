package com.pinocchio.santaclothes.apiserver.controller.dto;

import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import lombok.Builder;
import lombok.Value;

public class CaptureEventCreateRequestResponse {
	@Value
	@Builder
	public static class CaptureEventCreateRequest {
		String eventId;

		String imageId;

		@Builder.Default
		CaptureEventStatus eventStatus = CaptureEventStatus.START;
	}

	@Value
	public static class CaptureEventCreateResponse{
		String eventId;
	}
}
