package com.pinocchio.santaclothes.imageserver.apiclient.apiserver;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinocchio.santaclothes.common.type.CaptureEventStatus;
import com.pinocchio.santaclothes.imageserver.apiclient.apiserver.dto.CreateEventRequestDto;
import com.pinocchio.santaclothes.imageserver.test.SpringTest;

import reactor.test.StepVerifier;

class ApiServerApiClientTest extends SpringTest {
	@Autowired
	private ApiServerApiClient sut;

	@Test
	void createCaptureEvent() {
		CreateEventRequestDto request = new CreateEventRequestDto("userId", "abcdef", "bcdef",
			CaptureEventStatus.START);

		givenThat(
			post("/api/user/userId/capture/event")
				.withRequestBody(equalToJson(
					"{" +
						"	\"eventId\": \"abcdef\"," +
						"	\"imageId\": \"bcdef\"," +
						"	\"eventStatus\": \"START\"" +
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
