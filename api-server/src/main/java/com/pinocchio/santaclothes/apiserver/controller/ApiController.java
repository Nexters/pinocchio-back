package com.pinocchio.santaclothes.apiserver.controller;

import static java.util.stream.Collectors.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventCreateRequestResponse.CaptureEventCreateRequest;
import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventCreateRequestResponse.CaptureEventCreateResponse;
import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventResponse;
import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventUpdateRequest;
import com.pinocchio.santaclothes.apiserver.domain.CaptureEvent;
import com.pinocchio.santaclothes.apiserver.exception.ProblemModel;
import com.pinocchio.santaclothes.apiserver.service.CaptureService;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventUpdateDto;
import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(tags = "API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
	private final CaptureService captureService;

	@ApiOperation("캡쳐 이벤트 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "조회 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류", response = ProblemModel.class),
		@ApiResponse(code = 403, message = "인증 실패", response = ProblemModel.class)
	})
	@GetMapping("/capture/event")
	@ResponseStatus(HttpStatus.OK)
	public List<CaptureEventResponse> findEvents(@ApiParam("조회할 이벤트 상태") @RequestParam("status") CaptureEventStatus status) {
		return captureService.findByStatus(status).stream()
			.map(
				it -> CaptureEventResponse.builder()
					.eventId(it.getEventId())
					.imageId(it.getImageId())
					.status(it.getStatus())
					.build()
			)
			.collect(toList());
	}

	@ApiOperation("캡쳐 이벤트 조회")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "조회 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류", response = ProblemModel.class),
		@ApiResponse(code = 403, message = "인증 실패", response = ProblemModel.class)
	})
	@GetMapping("/capture/event/{eventId}")
	@ResponseStatus(HttpStatus.OK)
	public CaptureEventResponse fetchEvent(@PathVariable("eventId") String eventId) {
		CaptureEvent event = captureService.findById(eventId);
		return CaptureEventResponse.builder()
			.eventId(event.getEventId())
			.imageId(event.getImageId())
			.status(event.getStatus())
			.result(event.getResult())
			.build();
	}

	@ApiOperation("캡쳐 이벤트 생성")
	@ApiResponses(value = {
		@ApiResponse(code = 201, message = "생성 요청 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류", response = ProblemModel.class),
		@ApiResponse(code = 403, message = "인증 실패", response = ProblemModel.class)
	})
	@PostMapping("/capture/event")
	@ResponseStatus(HttpStatus.CREATED)
	public CaptureEventCreateResponse createEvent(@Valid CaptureEventCreateRequest request) {
		CaptureEvent captureEvent = CaptureEvent.builder()
			.eventId(request.getEventId())
			.imageId(request.getImageId())
			.status(request.getEventStatus())
			.build();
		captureService.save(captureEvent);
		return new CaptureEventCreateResponse(captureEvent.getEventId());
	}

	@ApiOperation("캡쳐 이벤트 상태 업데이트")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "업데이트 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류", response = ProblemModel.class),
		@ApiResponse(code = 403, message = "인증 실패", response = ProblemModel.class),
		@ApiResponse(code = 404, message = "이벤트 존재 안함, POST 요청으로 보내기", response = ProblemModel.class)
	})
	@PutMapping("/capture/event")
	@ResponseStatus(HttpStatus.OK)
	public CaptureEventResponse updateEvent(@Valid CaptureEventUpdateRequest request) {
		CaptureEventUpdateDto updateDto = CaptureEventUpdateDto.builder()
			.eventId(request.getEventId())
			.imageId(request.getImageId())
			.status(request.getStatus())
			.build();

		CaptureEvent event = captureService.update(updateDto);

		return CaptureEventResponse.builder()
			.eventId(event.getEventId())
			.imageId(event.getImageId())
			.status(event.getStatus())
			.build();
	}
}


