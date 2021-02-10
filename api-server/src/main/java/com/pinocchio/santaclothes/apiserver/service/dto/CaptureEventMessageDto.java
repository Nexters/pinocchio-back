package com.pinocchio.santaclothes.apiserver.service.dto;

import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CaptureEventMessageDto {
	String eventId;

	String imageId;

	CaptureEventStatus status;
}
