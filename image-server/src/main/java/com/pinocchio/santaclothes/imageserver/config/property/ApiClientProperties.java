package com.pinocchio.santaclothes.imageserver.config.property;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ApiClientProperties {
	private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 2_000;
	private static final int DEFAULT_READ_TIMEOUT_MILLIS = 2_000;

	private String url;

	private int connectTimeoutMillis = DEFAULT_CONNECT_TIMEOUT_MILLIS;

	private int readTimeoutMillis = DEFAULT_READ_TIMEOUT_MILLIS;
}
