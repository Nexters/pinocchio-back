package com.pinocchio.santaclothes.imageserver.config;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pinocchio.santaclothes.common.message.CaptureEventCreateMessage;
import com.pinocchio.santaclothes.common.message.CaptureEventProcessRequestMessage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class KafkaConfig {
	@Bean("captureCreateEmitter")
	public Sinks.Many<CaptureEventCreateMessage> captureCreateEmitter() {
		return Sinks.many().multicast().onBackpressureBuffer();
	}

	@Bean("captureProcessRequestEmitter")
	public Sinks.Many<CaptureEventProcessRequestMessage> captureProcessRequestEmitter() {
		return Sinks.many().multicast().onBackpressureBuffer();
	}

	@Bean
	public Supplier<Flux<CaptureEventCreateMessage>> captureCreate(Sinks.Many<CaptureEventCreateMessage> captureCreateEmitter) {
		return captureCreateEmitter::asFlux;
	}

	@Bean
	public Supplier<Flux<CaptureEventProcessRequestMessage>> captureProcess(Sinks.Many<CaptureEventProcessRequestMessage> captureProcessRequestEmitter) {
		return captureProcessRequestEmitter::asFlux;
	}
}
