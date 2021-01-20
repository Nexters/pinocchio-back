package com.pinocchio.santaclothes.apiserver.controller;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.*;

import org.junit.jupiter.api.Test;

import com.pinocchio.santaclothes.apiserver.test.ApiTest;

class ApiControllerTest extends ApiTest {

	@Test
	void healthCheck() {
		when()
			.get("/healthCheck")
			.then()
			.statusCode(200);
	}

	@Test
	void resumeExceptionTest() {
		when()
			.put("/capture/event/notExists/resume")
			.then()
			.statusCode(404)
			.contentType("application/problem+json")
			.body("eventId", equalTo("notExists"));
	}

}
