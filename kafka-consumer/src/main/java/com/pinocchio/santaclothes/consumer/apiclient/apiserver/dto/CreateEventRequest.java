package com.pinocchio.santaclothes.consumer.apiclient.apiserver.dto;

import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

import lombok.Value;

@Value
public class CreateEventRequest {
	String eventId;

	String imageId;

	CaptureEventStatus status = CaptureEventStatus.START;
}
