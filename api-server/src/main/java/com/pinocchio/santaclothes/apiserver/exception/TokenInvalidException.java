package com.pinocchio.santaclothes.apiserver.exception;

public class TokenInvalidException extends AttributeException {
	public TokenInvalidException(ExceptionReason reason) {
		super(reason);

		if (reason == ExceptionReason.SOCIAL_KEY_NOT_EXISTS) {
			with("ErrorCode", "invalid_client");
		} else {
			with("ErrorCode", "invalid_request");
		}
	}
}
