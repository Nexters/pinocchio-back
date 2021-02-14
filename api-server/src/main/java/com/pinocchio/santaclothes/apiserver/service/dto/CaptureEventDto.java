package com.pinocchio.santaclothes.apiserver.service.dto;

import org.springframework.lang.Nullable;

import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CaptureEventDto {
	String eventId;

	String imageId;

	String userId;

	@Nullable
	String result;

	CaptureEventStatus status;
}
