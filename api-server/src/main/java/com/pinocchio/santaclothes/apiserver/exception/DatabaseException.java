package com.pinocchio.santaclothes.apiserver.exception;

public class DatabaseException extends AttributeException {
	public DatabaseException(ExceptionReason reason) {
		super(reason);
	}
}
