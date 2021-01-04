package com.pinocchio.santaclothes.apiserver;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.Test;

import com.pinocchio.santaclothes.common.test.ApiTest;

class ApiControllerTest extends ApiTest {

	@Test
	void healthCheck() {
		when()
			.get("/healthCheck")
			.then()
			.statusCode(200);
	}

}
