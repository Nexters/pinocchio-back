package com.pinocchio.santaclothes.apiserver.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventCreateRequestResponse.CaptureEventCreateRequest;
import com.pinocchio.santaclothes.apiserver.controller.dto.CaptureEventUpdateRequest;
import com.pinocchio.santaclothes.apiserver.exception.EventInvalidException;
import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.service.CaptureService;
import com.pinocchio.santaclothes.apiserver.service.UserService;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventSaveRequestDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventUpdateRequestDto;
import com.pinocchio.santaclothes.apiserver.test.ApiTest;
import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

import io.restassured.http.ContentType;

class ApiControllerTest extends ApiTest {
	@MockBean
	private CaptureService captureService;

	@MockBean
	private UserService userService;

	// @Test
	// void findCaptureEventList() {
	// 	String mockAccessToken = "mockAccessToken";
	//
	// 	List<CaptureEvent> eventList = List.of(
	// 		CaptureEvent.builder()
	// 			.eventId("eventId1")
	// 			.imageId("imageId1")
	// 			.status(CaptureEventStatus.START)
	// 			.build(),
	//
	// 		CaptureEvent.builder()
	// 			.eventId("eventId2")
	// 			.imageId("imageId2")
	// 			.status(CaptureEventStatus.START)
	// 			.build(),
	//
	// 		CaptureEvent.builder()
	// 			.eventId("eventId3")
	// 			.imageId("imageId3")
	// 			.status(CaptureEventStatus.START)
	// 			.build()
	// 	);
	//
	// 	String authorization = String.format("Bearer %s", mockAccessToken);
	// 	mockLogin(mockAccessToken);
	//
	// 	BDDMockito.given(captureService.findByStatus(CaptureEventStatus.START))
	// 		.willReturn(eventList);
	//
	// 	given()
	// 		.queryParam("status", CaptureEventStatus.START)
	// 		.contentType(ContentType.JSON)
	// 		.header("Authorization", authorization)
	// 		.when()
	// 		.get("/api/capture/event")
	// 		.then()
	// 		.statusCode(200)
	// 		.body("[0].eventId", equalTo("eventId1"))
	// 		.body("[0].imageId", equalTo("imageId1"))
	// 		.body("[0].status", equalTo(CaptureEventStatus.START.name()))
	// 		.body("[1].eventId", equalTo("eventId2"))
	// 		.body("[1].imageId", equalTo("imageId2"))
	// 		.body("[1].status", equalTo(CaptureEventStatus.START.name()))
	// 		.body("[2].eventId", equalTo("eventId3"))
	// 		.body("[2].imageId", equalTo("imageId3"))
	// 		.body("[2].status", equalTo(CaptureEventStatus.START.name()));
	// }

	// @Test
	// void findCaptureEventListWithoutParam() {
	// 	String mockAccessToken = "mockAccessToken";
	// 	String authorization = String.format("Bearer %s", mockAccessToken);
	// 	mockLogin(mockAccessToken);
	//
	// 	given()
	// 		.header("Authorization", authorization)
	// 		.contentType(ContentType.JSON)
	// 		.when()
	// 		.get("/api/capture/event")
	// 		.then()
	// 		.statusCode(400);
	// }
	//
	// @Test
	// void findCaptureEventListWithoutLogin() {
	// 	given()
	// 		.queryParam("status", CaptureEventStatus.START)
	// 		.contentType(ContentType.JSON)
	// 		.when()
	// 		.get("/api/capture/event")
	// 		.then()
	// 		.statusCode(401);
	// }

	@Test
	void findCaptureEvent() {
		String mockAccessToken = "mockAccessToken";
		String eventId = "eventId1";

		CaptureEventDto event = CaptureEventDto.builder()
			.eventId(eventId)
			.imageId("imageId1")
			.status(CaptureEventStatus.DONE)
			.build();

		String authorization = String.format("Bearer %s", mockAccessToken);
		mockLogin(mockAccessToken);

		BDDMockito.given(captureService.findById(eventId))
			.willReturn(event);

		String userId = "userId";
		given()
			.contentType(ContentType.JSON)
			.header("Authorization", authorization)
			.when()
			.get("/api/user/{userId}/capture/event/{eventId}", userId, eventId)
			.then()
			.statusCode(200)
			.body("eventId", equalTo(eventId))
			.body("imageId", equalTo("imageId1"))
			.body("status", equalTo(CaptureEventStatus.DONE.name()));
	}

	@Test
	void findCaptureEventWithoutLogin() {
		String eventId = "eventId";
		String userId = "userId";
		given()
			.contentType(ContentType.JSON)
			.when()
			.get("/api/user/{userId}/capture/event/{eventId}", userId, eventId)
			.then()
			.statusCode(401);
	}

	@Test
	void createCaptureEvent() {
		String mockAccessToken = "mockAccessToken";
		String authorization = String.format("Bearer %s", mockAccessToken);
		mockLogin(mockAccessToken);

		String userId = "userId";
		String eventId = "eventId";
		String imageId = "imageId";

		CaptureEventSaveRequestDto event = CaptureEventSaveRequestDto.builder()
			.eventId(eventId)
			.imageId(imageId)
			.userId(userId)
			.status(CaptureEventStatus.START)
			.build();

		doNothing().when(captureService).save(event);

		CaptureEventCreateRequest request = CaptureEventCreateRequest.builder()
			.eventId(eventId)
			.imageId(imageId)
			.status(CaptureEventStatus.START)
			.build();

		given()
			.contentType(ContentType.JSON)
			.body(request)
			.header("Authorization", authorization)
			.when()
			.post("/api/user/{userId}/capture/event", userId)
			.then()
			.statusCode(201)
			.body("eventId", equalTo(eventId));
	}

	@Test
	void createCaptureEventWithoutLogin() {
		String eventId = "eventId";
		String imageId = "imageId";

		CaptureEventCreateRequest request = CaptureEventCreateRequest.builder()
			.eventId(eventId)
			.imageId(imageId)
			.status(CaptureEventStatus.START)
			.build();

		String userId = "userId";
		given()
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.post("/api/user/{userId}/capture/event", userId)
			.then()
			.statusCode(401);
	}

	@Test
	void createCaptureEventBadRequest() {
		String mockAccessToken = "mockAccessToken";
		String authorization = String.format("Bearer %s", mockAccessToken);
		mockLogin(mockAccessToken);

		String eventId = "eventId";

		CaptureEventCreateRequest request = CaptureEventCreateRequest.builder()
			.eventId(eventId)
			.imageId(null)
			.status(CaptureEventStatus.START)
			.build();

		String userId = "userId";
		given()
			.header("Authorization", authorization)
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.post("/api/user/{userId}/capture/event", userId)
			.then()
			.statusCode(400);
	}

	@Test
	void updateCaptureEvent() {
		String mockAccessToken = "mockAccessToken";
		String authorization = String.format("Bearer %s", mockAccessToken);
		mockLogin(mockAccessToken);

		String eventId = "eventId";
		String imageId = "imageId";
		CaptureEventStatus toStatus = CaptureEventStatus.EXTRACT;

		CaptureEventUpdateRequestDto dto = CaptureEventUpdateRequestDto.builder()
			.eventId(eventId)
			.imageId(imageId)
			.status(toStatus)
			.build();

		CaptureEventDto updatedEvent = CaptureEventDto.builder()
			.eventId(eventId)
			.imageId(imageId)
			.status(toStatus)
			.build();

		BDDMockito.given(captureService.update(dto))
			.willReturn(updatedEvent);

		CaptureEventUpdateRequest request = CaptureEventUpdateRequest.builder()
			.eventId(eventId)
			.imageId(imageId)
			.status(toStatus)
			.build();

		String userId = "userId";
		given()
			.header("Authorization", authorization)
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.put("/api/user/{userId}/capture/event", userId)
			.then()
			.statusCode(200)
			.body(
				"eventId", equalTo(eventId),
				"imageId", equalTo(imageId),
				"status", equalTo(toStatus.name())
			);
	}

	@Test
	void updateCaptureEventFailed() {
		String mockAccessToken = "mockAccessToken";
		String authorization = String.format("Bearer %s", mockAccessToken);
		mockLogin(mockAccessToken);

		String eventId = "eventId";
		String imageId = "imageId";
		CaptureEventStatus toStatus = CaptureEventStatus.EXTRACT;

		CaptureEventUpdateRequestDto dto = CaptureEventUpdateRequestDto.builder()
			.eventId(eventId)
			.imageId(imageId)
			.status(toStatus)
			.build();

		BDDMockito.given(captureService.update(dto))
			.willThrow(
				new EventInvalidException(new NoSuchElementException(), eventId, ExceptionReason.EVENT_NOT_EXIST));

		CaptureEventUpdateRequest request = CaptureEventUpdateRequest.builder()
			.eventId(eventId)
			.imageId(imageId)
			.status(toStatus)
			.build();

		String userId = "userId";
		given()
			.header("Authorization", authorization)
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.put("/api/user/{userId}/capture/event", userId)
			.then()
			.statusCode(404);
	}

	@Test
	void updateCaptureEventWithoutLogin() {
		String eventId = "eventId";
		String imageId = "imageId";
		CaptureEventStatus toStatus = CaptureEventStatus.EXTRACT;

		CaptureEventUpdateRequest request = CaptureEventUpdateRequest.builder()
			.eventId(eventId)
			.imageId(imageId)
			.status(toStatus)
			.build();

		String userId = "userId";
		given()
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.put("/api/user/{userId}/capture/event", userId)
			.then()
			.statusCode(401);
	}

	@Test
	void updateCaptureEventBadRequest() {
		String mockAccessToken = "mockAccessToken";
		String authorization = String.format("Bearer %s", mockAccessToken);
		mockLogin(mockAccessToken);

		CaptureEventUpdateRequest request = CaptureEventUpdateRequest.builder()
			.build();

		String userId = "userId";
		given()
			.header("Authorization", authorization)
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.put("/api/user/{userId}/capture/event", userId)
			.then()
			.statusCode(400);
	}

	private void mockLogin(String mockAccessToken) {
		BDDMockito.given(userService.isActiveToken(mockAccessToken))
			.willReturn(true);

		BDDMockito.given(userService.isExpired(mockAccessToken))
			.willReturn(false);
	}
}
