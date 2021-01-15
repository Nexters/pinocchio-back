package com.pinocchio.santaclothes.common.message;

import lombok.Value;

@Value
public class CaptureEventProcessRequestMessage {
	String eventId;
	String imageId;
}
