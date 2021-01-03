package com.pinnochio.santaclothes.apiserver.apiclient.fileserver;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.pinnochio.santaclothes.apiserver.apiclient.fileserver.dto.FileServerRequest;
import com.pinnochio.santaclothes.apiserver.test.SpringTest;

import reactor.test.StepVerifier;

class FileServerApiClientTest extends SpringTest {
	@Autowired
	private FileServerApiClient sut;

	@Test
	void upload() throws IOException {
		Resource mockInput = new ByteArrayResource("test".getBytes(StandardCharsets.UTF_8));

		FileServerRequest request = new FileServerRequest(1L, mockInput);
		byte[] fileByte = IOUtils.toByteArray(request.getFile().getInputStream());

		givenThat(
			post("/upload")
				.withMultipartRequestBody(aMultipart()
					.withName("userId")
					.withBody(equalTo(request.getUserId().toString())))
				.withMultipartRequestBody(aMultipart()
					.withName("file")
					.withBody(binaryEqualTo(fileByte))
				)
				.willReturn(okJson("{"
					+ "\"status\" : \"SUCCEED\","
					+ "\"message\" : \"성공\""
					+ "}"))
		);

		StepVerifier.create(this.sut.upload(request))
			.expectNext(true)
			.verifyComplete();
	}
}
