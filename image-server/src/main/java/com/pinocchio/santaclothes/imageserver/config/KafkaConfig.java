package com.pinocchio.santaclothes.imageserver.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
	// @Bean("captureCreateEmitter")
	// public Sinks.Many<CaptureEventCreateMessage> captureCreateEmitter() {
	// 	return Sinks.many().multicast().onBackpressureBuffer();
	// }
	//
	// @Bean("captureProcessRequestEmitter")
	// public Sinks.Many<CaptureEventProcessRequestMessage> captureProcessRequestEmitter() {
	// 	return Sinks.many().multicast().onBackpressureBuffer();
	// }
	//
	// @Bean
	// public Supplier<Flux<CaptureEventCreateMessage>> captureCreate(Sinks.Many<CaptureEventCreateMessage> captureCreateEmitter) {
	// 	return captureCreateEmitter::asFlux;
	// }
	//
	// @Bean
	// public Supplier<Flux<CaptureEventProcessRequestMessage>> captureProcess(Sinks.Many<CaptureEventProcessRequestMessage> captureProcessRequestEmitter) {
	// 	return captureProcessRequestEmitter::asFlux;
	// }
}
