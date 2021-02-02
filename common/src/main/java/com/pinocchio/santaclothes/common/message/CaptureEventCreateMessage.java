package com.pinocchio.santaclothes.common.message;

import lombok.Value;

@Value
public class CaptureEventCreateMessage {
	String eventId;
	String imageId;
}
