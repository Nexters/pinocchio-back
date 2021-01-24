package com.pinocchio.santaclothes.apiserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().registerModules(
			new JavaTimeModule(),
			new ProblemModule(),
			new ConstraintViolationProblemModule())
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
}
