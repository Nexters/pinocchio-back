package com.pinocchio.santaclothes.imageserver.apiclient.apiserver.dto;

import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

import lombok.Value;

@Value
public class CreateEventRequest {
	String eventId;

	String imageId;

	CaptureEventStatus eventStatus;
}
