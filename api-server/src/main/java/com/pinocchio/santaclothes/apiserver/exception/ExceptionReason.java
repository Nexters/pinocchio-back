package com.pinocchio.santaclothes.apiserver.exception;

public enum ExceptionReason {
	TOKEN_EXPIRED,
	TOKEN_NOT_FOUND,
	DUPLICATE_ENTITY,
	EVENT_SHOULD_RETRY,
}
