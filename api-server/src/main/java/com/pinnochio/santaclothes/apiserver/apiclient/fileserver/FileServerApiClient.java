package com.pinnochio.santaclothes.apiserver.apiclient.fileserver;

import static org.springframework.web.reactive.function.BodyInserters.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.pinnochio.santaclothes.apiserver.apiclient.fileserver.dto.FileServerRequest;
import com.pinnochio.santaclothes.apiserver.apiclient.fileserver.dto.FileServerResponse;

import reactor.core.publisher.Mono;

@Component
public class FileServerApiClient {
	private final WebClient webClient;

	public FileServerApiClient(@Qualifier("fileServerWebClient") WebClient webClient) {
		this.webClient = webClient;
	}

	public Mono<Boolean> upload(FileServerRequest request) {
		return webClient.post()
			.uri("/upload")
			.body(fromMultipartData(createMultipartBody(request)))
			.retrieve()
			.bodyToMono(FileServerResponse.class)
			.map(FileServerResponse::isSuccess);
	}

	private MultiValueMap<String, HttpEntity<?>> createMultipartBody(FileServerRequest request) {
		MultipartBodyBuilder builder = new MultipartBodyBuilder();
		builder.part("userId", request.getUserId());
		builder.part("file", request.getFile()).filename("test");
		return builder.build();
	}
}
