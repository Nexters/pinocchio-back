package com.pinocchio.santaclothes.imageserver.apiclient.apiserver.dto;

import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

import lombok.Value;

@Value
public class CreateEventRequestDto {
	String userId;

	String eventId;

	String imageId;

	CaptureEventStatus eventStatus;
}
