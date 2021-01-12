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

		// 2048 바이트씩 읽는지 wiremock 내부 구현 확인 필요
		// Todo 실제 데이터 값과 비교할 수 있는 방법?
		StepVerifier.create(this.sut.fetchImage(imageName))
			.expectNextCount(8)
			.verifyComplete();
	}
}
