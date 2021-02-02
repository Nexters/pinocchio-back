package com.pinocchio.santaclothes.apiserver.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class AttributeException extends RuntimeException {
	private final Map<String, String> attributes = new HashMap<>();
	private final ExceptionReason reason;

	public AttributeException(ExceptionReason reason) {
		super();
		this.reason = reason;
		with("reason", reason.name());

	}

	public AttributeException(Throwable cause, ExceptionReason reason) {
		super(cause);
		this.reason = reason;
		with("reason", reason.name());
	}

	public AttributeException with(String key, String value) {
		attributes.put(key, value);
		return this;
	}

}
