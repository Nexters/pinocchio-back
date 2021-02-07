package com.pinocchio.santaclothes.apiserver.service.dto;

import org.springframework.lang.Nullable;

import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CaptureEventSaveRequestDto {
	String eventId;

	String imageId;

	String userId;

	@Nullable
	String result;

	@Builder.Default
	CaptureEventStatus status = CaptureEventStatus.START;
}
