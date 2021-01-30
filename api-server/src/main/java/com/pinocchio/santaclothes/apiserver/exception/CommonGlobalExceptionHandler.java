package com.pinocchio.santaclothes.apiserver.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

public abstract class CommonGlobalExceptionHandler implements ProblemHandling {
	// 400 BadRequest
	// 500 InternalServerError
	// 403 Forbidden

	// Service unavailable

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Problem> handleRuntimeException(RuntimeException exception, NativeWebRequest request) {
		return createInternalServerProblem(exception, request);
	}

	protected ResponseEntity<Problem> createInternalServerProblem(Exception e, NativeWebRequest request) {
		Problem problem = Problem.builder()
			.withTitle(Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
			.withStatus(Status.INTERNAL_SERVER_ERROR)
			.build();
		return create(e, problem, request);
	}

	protected void applyAttribute(ProblemBuilder builder, AttributeException e) {
		e.getAttributes().forEach(builder::with);
	}

}
