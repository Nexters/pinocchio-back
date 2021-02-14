package com.pinocchio.santaclothes.imageserver.apiclient.apiserver.dto;

import lombok.Value;

@Value
public class CreateEventRequestDto {
	String userId;

	String eventId;

	String imageId;
}
