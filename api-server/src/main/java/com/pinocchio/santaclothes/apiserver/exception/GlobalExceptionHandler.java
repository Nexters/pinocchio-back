package com.pinocchio.santaclothes.apiserver.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;

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

	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<Problem> handleTokenExpiredException(
		TokenExpiredException exception,
		NativeWebRequest request
	) {
		return createTokenExpiredProblem(exception, request);
	}

	protected ResponseEntity<Problem> createResumeProblem(EventResumeException e, NativeWebRequest request) {
		ProblemBuilder builder = Problem.builder()
			.withTitle(Status.NOT_FOUND.getReasonPhrase())
			.withStatus(Status.NOT_FOUND);

		applyAttribute(builder, e);

		Problem problem = builder.build();
		return create(e, problem, request);
	}

	protected ResponseEntity<Problem> createDuplicateProblem(DuplicateUserException e, NativeWebRequest request) {
		ProblemBuilder builder = Problem.builder()
			.withTitle(Status.CONFLICT.getReasonPhrase())
			.withStatus(Status.CONFLICT);

		applyAttribute(builder, e);

		Problem problem = builder.build();
		return create(e, problem, request);
	}

	protected ResponseEntity<Problem> createTokenExpiredProblem(TokenExpiredException e, NativeWebRequest request) {
		ProblemBuilder builder = Problem.builder()
			.withTitle(Status.FORBIDDEN.getReasonPhrase())
			.withStatus(Status.FORBIDDEN);

		applyAttribute(builder, e);

		Problem problem = builder.build();
		return create(e, problem, request);
	}

}
