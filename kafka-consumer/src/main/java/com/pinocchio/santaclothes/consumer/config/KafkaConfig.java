package com.pinocchio.santaclothes.consumer.config;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pinocchio.santaclothes.common.message.CaptureEventCreateMessage;
import com.pinocchio.santaclothes.common.message.CaptureEventProcessDoneMessage;
import com.pinocchio.santaclothes.common.message.CaptureEventProcessRequestMessage;
import com.pinocchio.santaclothes.consumer.service.CaptureEventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaConfig {
	private final CaptureEventService captureEventService;

	@Bean
	public Function<Flux<CaptureEventCreateMessage>, Mono<Void>> captureCreate() {
		return flux -> flux.doOnNext(captureEventService::create)
			.then();
	}

	@Bean
	public Function<Flux<CaptureEventProcessRequestMessage>, Mono<Void>> captureProcessRequest() {
		return flux -> flux.doOnNext(captureEventService::processRequest)
			.then();
	}

	@Bean
	public Function<Flux<CaptureEventProcessDoneMessage>, Mono<Void>> captureProcessDone() {
		return flux -> flux.doOnNext(captureEventService::processDone)
			.then();
	}
}
