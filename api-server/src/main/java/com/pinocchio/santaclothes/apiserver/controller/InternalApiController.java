package com.pinocchio.santaclothes.apiserver.controller;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventResponse;
import com.pinocchio.santaclothes.apiserver.exception.ProblemModel;
import com.pinocchio.santaclothes.apiserver.service.CaptureService;
import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(tags = "INTERNAL API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/api")
public class InternalApiController {
	private final CaptureService captureService;

	@ApiOperation("모든 유저의 캡쳐 이벤트 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "조회 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류", response = ProblemModel.class),
		@ApiResponse(code = 401, message = "인증 실패", response = ProblemModel.class)
	})
	@GetMapping("/capture/event")
	@ResponseStatus(HttpStatus.OK)
	public List<CaptureEventResponse> findEvents(
		@ApiParam("조회할 이벤트 상태") @RequestParam("status") CaptureEventStatus status) {
		return captureService.findByStatus(status).stream()
			.map(
				it -> CaptureEventResponse.builder()
					.eventId(it.getEventId())
					.userId(it.getUserId())
					.imageId(it.getImageId())
					.status(it.getStatus())
					.build()
			)
			.collect(toList());
	}
}
