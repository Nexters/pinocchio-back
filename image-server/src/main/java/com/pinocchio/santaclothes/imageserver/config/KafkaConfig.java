package com.pinocchio.santaclothes.imageserver.config;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pinocchio.santaclothes.common.message.CaptureEventMessage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class KafkaConfig {
	@Bean("captureEmitter")
	public Sinks.Many<CaptureEventMessage> emitter() {
		return Sinks.many().multicast().onBackpressureBuffer();
	}

	@Bean
	public Supplier<Flux<CaptureEventMessage>> captureCreate(Sinks.Many<CaptureEventMessage> captureCreator) {
		return captureCreator::asFlux;
	}
}
