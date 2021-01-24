package com.pinocchio.santaclothes.apiserver.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class AttributeException extends RuntimeException {
	private final Map<String, String> attributes = new HashMap<>();

	public AttributeException(Throwable cause) {
		super(cause);
	}

	public AttributeException with(String key, String value) {
		attributes.put(key, value);
		return this;
	}
}
