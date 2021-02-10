package com.pinocchio.santaclothes.common.message;

import lombok.Value;

@Value
public class CaptureEventCreateMessage {
	String userId;
	String eventId;
	String imageId;
}
