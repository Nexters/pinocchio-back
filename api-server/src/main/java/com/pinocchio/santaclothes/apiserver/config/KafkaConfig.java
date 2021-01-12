package com.pinocchio.santaclothes.apiserver.config;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pinocchio.santaclothes.apiserver.service.EventMessageService;
import com.pinocchio.santaclothes.common.message.CaptureEventMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaConfig {
	private final EventMessageService eventMessageService;

	@Bean
	public Function<Flux<CaptureEventMessage>, Mono<Void>> captureCreate() {
		return flux -> flux.doOnNext(eventMessageService::captureEvent)
			.then();
	}
}
