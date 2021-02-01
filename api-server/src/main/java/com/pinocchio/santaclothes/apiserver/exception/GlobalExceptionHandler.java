package com.pinocchio.santaclothes.apiserver.exception;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;

@ControllerAdvice
public class GlobalExceptionHandler extends CommonGlobalExceptionHandler {
	private static final Map<ExceptionReason, Status> REASON_STATUS_MAP = Map.of(
		ExceptionReason.DUPLICATE_ENTITY, Status.CONFLICT,
		ExceptionReason.EVENT_SHOULD_RETRY, Status.NOT_FOUND,
		ExceptionReason.INVALID_REFRESH_TOKEN, Status.BAD_REQUEST,
		ExceptionReason.SOCIAL_KEY_NOT_EXISTS, Status.BAD_REQUEST
	);

	@ExceptionHandler(EventInvalidException.class)
	public ResponseEntity<Problem> handleEventInvalidException(
		EventInvalidException exception,
		NativeWebRequest request
	) {
		return createEventProblem(exception, request);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<Problem> handleDatabaseException(
		DatabaseException exception,
		NativeWebRequest request
	) {
		return createDuplicateProblem(exception, request);
	}

	@ExceptionHandler(TokenInvalidException.class)
	public ResponseEntity<Problem> handleTokenInvalidException(
		TokenInvalidException exception,
		NativeWebRequest request
	) {
		return createTokenInvalidProblem(exception, request);
	}

	protected ResponseEntity<Problem> createEventProblem(EventInvalidException e, NativeWebRequest request) {
		Status status = REASON_STATUS_MAP.getOrDefault(e.getReason(), Status.BAD_REQUEST);

		ProblemBuilder builder = Problem.builder()
			.withTitle(status.getReasonPhrase())
			.withStatus(status);

		applyAttribute(builder, e);

		Problem problem = builder.build();
		return create(e, problem, request);
	}

	protected ResponseEntity<Problem> createDuplicateProblem(DatabaseException e, NativeWebRequest request) {
		Status status = REASON_STATUS_MAP.getOrDefault(e.getReason(), Status.BAD_REQUEST);

		ProblemBuilder builder = Problem.builder()
			.withTitle(status.getReasonPhrase())
			.withStatus(status);

		applyAttribute(builder, e);

		Problem problem = builder.build();
		return create(e, problem, request);
	}

	protected ResponseEntity<Problem> createTokenInvalidProblem(TokenInvalidException e, NativeWebRequest request) {
		Status status = REASON_STATUS_MAP.getOrDefault(e.getReason(), Status.UNAUTHORIZED);

		ProblemBuilder builder = Problem.builder()
			.withTitle(status.getReasonPhrase())
			.withStatus(status);

		applyAttribute(builder, e);

		Problem problem = builder.build();
		return create(e, problem, request);
	}

}
