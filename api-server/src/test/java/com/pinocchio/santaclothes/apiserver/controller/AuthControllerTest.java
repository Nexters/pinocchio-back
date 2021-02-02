package com.pinocchio.santaclothes.apiserver.controller;

import static com.pinocchio.santaclothes.apiserver.support.JsonSupports.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pinocchio.santaclothes.apiserver.controller.dto.LoginRequest;
import com.pinocchio.santaclothes.apiserver.controller.dto.RefreshRequest;
import com.pinocchio.santaclothes.apiserver.controller.dto.RegisterRequest;
import com.pinocchio.santaclothes.apiserver.domain.UserAuth;
import com.pinocchio.santaclothes.apiserver.exception.DatabaseException;
import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.exception.TokenInvalidException;
import com.pinocchio.santaclothes.apiserver.service.UserService;
import com.pinocchio.santaclothes.apiserver.test.ApiTest;
import com.pinocchio.santaclothes.apiserver.type.LoginType;

import io.restassured.http.ContentType;

class AuthControllerTest extends ApiTest {
	@MockBean
	private UserService userService;

	@Test
	void register() {
		RegisterRequest registerRequest = new RegisterRequest("socialId", LoginType.KAKAO, "nickName");

		given()
			.contentType(ContentType.JSON)
			.body(registerRequest)
			.when()
			.post("/auth/register")
			.then()
			.statusCode(201);
	}

	@Test
	void registerTwice() {
		RegisterRequest registerRequest = new RegisterRequest("socialId", LoginType.KAKAO, "nickName");

		doThrow(new DatabaseException(ExceptionReason.DUPLICATE_ENTITY))
			.when(userService)
			.register(registerRequest.getSocialId(), registerRequest.getNickName());

		given()
			.contentType(ContentType.JSON)
			.body(registerRequest)
			.when()
			.post("/auth/register")
			.then()
			.statusCode(409);
	}

	@Test
	void registerBadRequest() {
		RegisterRequest registerRequest = new RegisterRequest("socialId", null, "nickName");

		doThrow(new DatabaseException(ExceptionReason.DUPLICATE_ENTITY))
			.when(userService)
			.register(registerRequest.getSocialId(), registerRequest.getNickName());

		given()
			.contentType(ContentType.JSON)
			.body(registerRequest)
			.when()
			.post("/auth/register")
			.then()
			.statusCode(400);
	}

	@Test
	void login() throws JsonProcessingException {
		String socialId = "socialId";
		String userId = "userId";
		Instant now = Instant.now();
		Instant expiredDateTime = now.plus(30, ChronoUnit.DAYS);
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		UserAuth auth = UserAuth.builder()
			.id(1L)
			.userId(userId)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.expireDateTime(expiredDateTime)
			.createdDate(now)
			.build();

		BDDMockito.given(userService.login(socialId))
			.willReturn(auth);
		LoginRequest loginRequest = new LoginRequest(socialId);

		given()
			.contentType(ContentType.JSON)
			.body(loginRequest)
			.when()
			.post("/auth/login")
			.then()
			.statusCode(200)
			.body(
				"accessToken", equalTo(accessToken),
				"refreshToken", equalTo(refreshToken),
				"expireDateTime", equalTo(JSON_MAPPER.writeValueAsString(expiredDateTime).replaceAll("\"", ""))
			);
	}

	@Test
	void loginWithoutRegister() {
		String socialId = "socialId";
		LoginRequest loginRequest = new LoginRequest(socialId);

		BDDMockito.given(userService.login(socialId))
			.willThrow(new TokenInvalidException(ExceptionReason.SOCIAL_KEY_NOT_EXISTS));

		given()
			.contentType(ContentType.JSON)
			.body(loginRequest)
			.when()
			.post("/auth/login")
			.then()
			.statusCode(400);
	}

	@Test
	void refreshAccessToken() throws JsonProcessingException {
		RefreshRequest request = new RefreshRequest("refreshToken");
		String userId = "userId";
		Instant now = Instant.now();
		Instant expiredDateTime = now.plus(30, ChronoUnit.DAYS);
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		UserAuth auth = UserAuth.builder()
			.id(1L)
			.userId(userId)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.expireDateTime(expiredDateTime)
			.createdDate(now)
			.build();

		BDDMockito.given(userService.refresh(request.getRefreshToken()))
			.willReturn(auth);

		given()
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.put("/auth/accessToken")
			.then()
			.statusCode(200)
			.body(
				"accessToken", equalTo(accessToken),
				"refreshToken", equalTo(refreshToken),
				"expireDateTime", equalTo(JSON_MAPPER.writeValueAsString(expiredDateTime).replaceAll("\"", ""))
			);
	}

	@Test
	void refreshNotExistAccessToken(){
		RefreshRequest request = new RefreshRequest("refreshToken");

		BDDMockito.given(userService.refresh(request.getRefreshToken()))
			.willThrow(new TokenInvalidException(ExceptionReason.INVALID_REFRESH_TOKEN));

		given()
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.put("/auth/accessToken")
			.then()
			.statusCode(401);
	}

	@Test
	void refreshTokenExpired() {
		RefreshRequest request = new RefreshRequest("refreshToken");

		BDDMockito.given(userService.refresh(request.getRefreshToken()))
			.willThrow(new TokenInvalidException(ExceptionReason.INVALID_REFRESH_TOKEN));

		given()
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.put("/auth/accessToken")
			.then()
			.statusCode(401);
	}
}
