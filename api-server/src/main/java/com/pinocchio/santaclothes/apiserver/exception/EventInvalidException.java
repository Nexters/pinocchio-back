package com.pinocchio.santaclothes.apiserver.exception;

public class EventInvalidException extends AttributeException {

	public EventInvalidException(String eventId, ExceptionReason reason) {
		super(reason);
		with("eventId", eventId);
	}

	public EventInvalidException(Throwable cause, String eventId, ExceptionReason reason) {
		super(cause, reason);
		with("eventId", eventId);
	}
}
