package com.pinocchio.santaclothes.apiserver.exception;

import lombok.Value;

@Value
public class ProblemModel {
	String title;

	int status;

	ExceptionReason reason;
}
