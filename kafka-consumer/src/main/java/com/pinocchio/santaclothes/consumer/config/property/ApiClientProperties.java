package com.pinocchio.santaclothes.consumer.config.property;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Validated
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ApiClientProperties {
	private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 2_000;
	private static final int DEFAULT_READ_TIMEOUT_MILLIS = 2_000;

	@URL
	@NotBlank
	private String url;

	@Positive
	private int connectTimeoutMillis = DEFAULT_CONNECT_TIMEOUT_MILLIS;

	private int readTimeoutMillis = DEFAULT_READ_TIMEOUT_MILLIS;
}
