package com.pinocchio.santaclothes.apiserver.support;

import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonSupports {
	public final static ObjectMapper JSON_MAPPER;

	static {
		JSON_MAPPER = new ObjectMapper().registerModules(
			new JavaTimeModule(),
			new ProblemModule(),
			new ConstraintViolationProblemModule())
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
}
