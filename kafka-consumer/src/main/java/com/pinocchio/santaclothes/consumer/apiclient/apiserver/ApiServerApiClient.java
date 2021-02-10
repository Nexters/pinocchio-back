package com.pinocchio.santaclothes.consumer.apiclient.apiserver;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.pinocchio.santaclothes.consumer.apiclient.apiserver.dto.CreateEventRequest;
import com.pinocchio.santaclothes.consumer.apiclient.apiserver.dto.CreateEventRequestDto;
import com.pinocchio.santaclothes.consumer.apiclient.apiserver.dto.CreateEventResponse;
import com.pinocchio.santaclothes.consumer.apiclient.apiserver.dto.FinishEventRequest;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ApiServerApiClient {
	private final WebClient webClient;

	public ApiServerApiClient(@Qualifier("apiServerWebClient") WebClient webClient) {
		this.webClient = webClient;
	}

	public Mono<String> createEvent(CreateEventRequestDto requestDto) {
		CreateEventRequest request = new CreateEventRequest(requestDto.getEventId(), requestDto.getImageId());
		return webClient.post()
			.uri("/api/user/{userId}/capture/event", requestDto.getUserId())
			.bodyValue(request)
			.retrieve()
			.onStatus(HttpStatus::is4xxClientError,
				ClientResponse::createException)
			.bodyToMono(CreateEventResponse.class)
			.map(CreateEventResponse::getEventId);
	}

	// public Mono<Void> finishEvent(FinishEventRequest request) {
	// 	return webClient.put()
	// 		.uri("/api/user/{userId}/capture/event/{eventId}", request.getUserId(), request.getEventId())
	// 		.bodyValue(request)
	// 		.retrieve()
	// 		.onStatus(HttpStatus::is4xxClientError,
	// 			ClientResponse::createException)
	// 		.bodyToMono(Void.class);
	// }
}
