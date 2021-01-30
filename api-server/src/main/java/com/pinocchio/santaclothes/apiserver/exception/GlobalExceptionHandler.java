package com.pinocchio.santaclothes.apiserver.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;

@ControllerAdvice
public class GlobalExceptionHandler extends CommonGlobalExceptionHandler {
	@ExceptionHandler(EventResumeException.class)
	public ResponseEntity<Problem> handleEventResumeException(
		EventResumeException exception,
		NativeWebRequest request
	) {
		return createResumeProblem(exception, request);
	}

	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<Problem> handleDuplicateUserException(
		DuplicateUserException exception,
		NativeWebRequest request
	) {
		return createDuplicateProblem(exception, request);
	}

}
