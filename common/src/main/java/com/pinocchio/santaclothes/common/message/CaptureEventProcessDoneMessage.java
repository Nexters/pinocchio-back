package com.pinocchio.santaclothes.common.message;

import lombok.Value;

@Value
public class CaptureEventProcessDoneMessage {
	String eventId;
	String imageId;
	String result;
}
