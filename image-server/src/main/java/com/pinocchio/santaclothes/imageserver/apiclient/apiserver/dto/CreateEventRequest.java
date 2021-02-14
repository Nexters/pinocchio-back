package com.pinocchio.santaclothes.imageserver.apiclient.apiserver.dto;

import lombok.Value;

@Value
public class CreateEventRequest {
	String eventId;

	String imageId;
}
