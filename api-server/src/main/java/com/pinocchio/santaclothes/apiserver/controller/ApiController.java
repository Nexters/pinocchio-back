package com.pinocchio.santaclothes.apiserver.controller;

import static com.pinocchio.santaclothes.apiserver.support.ObjectSupports.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventCreateRequestResponse.CaptureEventCreateRequest;
import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventCreateRequestResponse.CaptureEventCreateResponse;
import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventResponse;
import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventUpdateRequest;
import com.pinocchio.santaclothes.apiserver.controller.dto.LabelResultRequest;
import com.pinocchio.santaclothes.apiserver.exception.ProblemModel;
import com.pinocchio.santaclothes.apiserver.service.CaptureService;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventSaveRequestDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventUpdateRequestDto;
import com.pinocchio.santaclothes.apiserver.support.JsonSupports;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(tags = "API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
	private final CaptureService captureService;

	@ApiOperation("캡쳐 이벤트 조회")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "조회 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류", response = ProblemModel.class),
		@ApiResponse(code = 401, message = "인증 실패", response = ProblemModel.class)
	})
	@GetMapping("/user/{userId}/capture/event/{eventId}")
	@ResponseStatus(HttpStatus.OK)
	public CaptureEventResponse fetchEvent(
		@PathVariable String userId,
		@PathVariable("eventId") String eventId) {
		CaptureEventDto event = captureService.findById(eventId);
		return CaptureEventResponse.builder()
			.eventId(event.getEventId())
			.imageId(event.getImageId())
			.status(event.getStatus())
			.result(event.getResult())
			.build();
	}

	@ApiOperation("캡쳐 이벤트 상태 업데이트")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "업데이트 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류", response = ProblemModel.class),
		@ApiResponse(code = 401, message = "인증 실패", response = ProblemModel.class),
		@ApiResponse(code = 404, message = "이벤트 존재 안함, POST 요청으로 보내기", response = ProblemModel.class)
	})
	@PutMapping("/user/{userId}/capture/event/{eventId}")
	@ResponseStatus(HttpStatus.OK)
	public CaptureEventResponse updateEvent(
		@PathVariable String userId,
		@PathVariable String eventId,
		@RequestBody @Valid CaptureEventUpdateRequest request
	) {
			LabelResultRequest requestResult = request.getResult();
			String result = ifNotNullApply(requestResult, JsonSupports::toJsonString);

			CaptureEventUpdateRequestDto updateDto = CaptureEventUpdateRequestDto.builder()
				.eventId(eventId)
				.imageId(request.getImageId())
				.status(request.getStatus())
				.result(result)
				.build();

			CaptureEventDto event = captureService.update(updateDto);

			return CaptureEventResponse.builder()
				.eventId(event.getEventId())
				.imageId(event.getImageId())
				.result(event.getResult())
				.status(event.getStatus())
				.build();
	}

	@ApiOperation("캡쳐 이벤트 생성")
	@ApiResponses(value = {
		@ApiResponse(code = 201, message = "생성 요청 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류", response = ProblemModel.class),
		@ApiResponse(code = 401, message = "인증 실패", response = ProblemModel.class)
	})
	@PostMapping("/user/{userId}/capture/event")
	@ResponseStatus(HttpStatus.CREATED)
	public CaptureEventCreateResponse createEvent(
		@PathVariable String userId,
		@RequestBody @Valid CaptureEventCreateRequest request
	) {
		CaptureEventSaveRequestDto saveDto = CaptureEventSaveRequestDto.builder()
			.userId(userId)
			.eventId(request.getEventId())
			.status(request.getEventStatus())
			.imageId(request.getImageId())
			.build();
		captureService.save(saveDto);
		return new CaptureEventCreateResponse(saveDto.getEventId());
	}
}


