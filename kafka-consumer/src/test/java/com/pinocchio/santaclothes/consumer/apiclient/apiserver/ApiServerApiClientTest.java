package com.pinocchio.santaclothes.consumer.apiclient.apiserver;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinocchio.santaclothes.consumer.apiclient.apiserver.dto.CreateEventRequest;
import com.pinocchio.santaclothes.consumer.test.SpringTest;

import reactor.test.StepVerifier;

class ApiServerApiClientTest extends SpringTest {
	@Autowired
	private ApiServerApiClient sut;

	@Test
	void createCaptureEvent() {
		CreateEventRequest request = new CreateEventRequest("abcdef", "bcdef");

		givenThat(
			post("/capture/event")
				.withRequestBody(equalToJson(
					"{" +
						"	\"eventId\": \"abcdef\"," +
						"	\"imageId\": \"bcdef\"" +
						"}"
				))
				.willReturn(
					created()
						.withHeader("Content-Type", "application/json")
						.withBody(
							"{" +
								"	\"eventId\": \"abcdef\"" +
								"}"
						)
				)
		);

		StepVerifier.create(this.sut.createEvent(request))
			.expectNext("abcdef")
			.verifyComplete();
	}
}
