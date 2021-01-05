package com.pinocchio.santaclothes.apiserver.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.pinocchio.santaclothes.apiserver.ApiServerApplication;

import io.restassured.RestAssured;

@SpringBootTest(
	classes = {ApiServerApplication.class},
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
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
