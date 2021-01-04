package com.pinocchio.santaclothes.common.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest {
	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.reset();
		RestAssured.port = port;
	}

	@AfterEach
	void tearDown() {
		RestAssured.reset();
	}

}
