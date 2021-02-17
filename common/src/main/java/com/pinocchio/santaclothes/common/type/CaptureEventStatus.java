package com.pinocchio.santaclothes.common.type;

public enum CaptureEventStatus {
	START,
	EXTRACT,
	REPORT,
	DONE;

	public boolean isAfter(CaptureEventStatus status){
		return this.ordinal() > status.ordinal();
	}
}
