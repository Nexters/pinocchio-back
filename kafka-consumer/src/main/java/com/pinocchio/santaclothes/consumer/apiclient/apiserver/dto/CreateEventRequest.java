package com.pinocchio.santaclothes.consumer.apiclient.apiserver.dto;

import lombok.Value;

@Value
public class CreateEventRequest {
	String eventId;

	String imageId;
}
