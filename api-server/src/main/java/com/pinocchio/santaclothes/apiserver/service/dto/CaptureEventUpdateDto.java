package com.pinocchio.santaclothes.apiserver.service.dto;

import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CaptureEventUpdateDto {
	String eventId;

	String imageId;

	CaptureEventStatus status;
}
