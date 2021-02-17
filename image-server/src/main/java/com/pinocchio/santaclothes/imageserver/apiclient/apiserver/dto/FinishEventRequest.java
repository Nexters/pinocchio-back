package com.pinocchio.santaclothes.imageserver.apiclient.apiserver.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FinishEventRequest {
	String eventId;

	String imageId;

	@Builder.Default
	String status = "DONE";

	String result;
}
