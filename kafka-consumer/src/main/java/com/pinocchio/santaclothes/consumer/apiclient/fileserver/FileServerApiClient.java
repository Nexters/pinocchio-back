package com.pinocchio.santaclothes.consumer.apiclient.fileserver;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Component
public class FileServerApiClient {
	private final WebClient webClient;

	public FileServerApiClient(@Qualifier("fileServerWebClient") WebClient webClient) {
		this.webClient = webClient;
	}

	public Flux<DataBuffer> fetchImage(String imageName) {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/images/{imageName}")
				.build(imageName)
			)
			.retrieve()
			.bodyToFlux(DataBuffer.class);
	}
}
