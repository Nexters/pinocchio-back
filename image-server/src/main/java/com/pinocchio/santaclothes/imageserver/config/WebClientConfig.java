package com.pinocchio.santaclothes.imageserver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.pinocchio.santaclothes.imageserver.config.property.ApiClientProperties;

@Configuration
public class WebClientConfig {
	@Bean("apiServerApiClientProperties")
	@ConfigurationProperties(prefix = "api.api-server")
	ApiClientProperties apiServerApiClientProperties() {
		return new ApiClientProperties();
	}

	@Bean("apiServerWebClient")
	WebClient apiServerWebClient(
		WebClient.Builder webClientBuilder,
		@Qualifier("apiServerApiClientProperties") ApiClientProperties apiClientProperties
	) {
		return webClientBuilder.baseUrl(apiClientProperties.getUrl())
			.defaultHeaders(headers ->
				headers.setContentType(MediaType.APPLICATION_JSON)
			).build();
	}
}
