package com.pinocchio.santaclothes.consumer.apiclient.fileserver;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinocchio.santaclothes.consumer.test.SpringTest;

import reactor.test.StepVerifier;

class FileServerApiClientTest extends SpringTest {
	@Autowired
	private FileServerApiClient sut;

	@Test
	void fetchImage() {
		String imageName = "abcdef";
		String mockFileName = "test.png";

		givenThat(
			get("/images/" + imageName)
				.willReturn(
					ok().withBodyFile(mockFileName)
				)
		);

		StepVerifier.create(this.sut.fetchImage(imageName))
			.thenConsumeWhile(it -> true)
			.verifyComplete();
	}
}
