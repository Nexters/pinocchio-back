package com.pinocchio.santaclothes.apiserver.exception;

public class TokenInvalidException extends AttributeException {
	public TokenInvalidException(ExceptionReason reason) {
		super(reason);
	}
}
