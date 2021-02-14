package com.pinocchio.santaclothes.imageserver.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.pinocchio.santaclothes.imageserver.ImageServerApplication;

@SpringBootTest(classes = {ImageServerApplication.class})
@ActiveProfiles("test")
public abstract class SpringTest {
	@BeforeEach
	public void setUp() {
		WireMock.reset();
	}

	@AfterEach
	public void tearDown() {
		WireMock.reset();
	}
}
