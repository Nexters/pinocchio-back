package com.pinnochio.santaclothes.apiserver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.pinnochio.santaclothes.apiserver.config.property.ApiClientProperties;

@Configuration
public class WebClientConfig {
	@Bean("fileServerApiClientProperties")
	@ConfigurationProperties(prefix = "api.file-server")
	ApiClientProperties fileServerApiClientProperties() {
		return new ApiClientProperties();
	}

	@Bean("fileServerWebClient")
	WebClient fileServerWebClient(
		WebClient.Builder webClientBuilder,
		@Qualifier("fileServerApiClientProperties") ApiClientProperties apiClientProperties
	) {
		return webClientBuilder.baseUrl(apiClientProperties.getUrl())
			.defaultHeaders(headers ->
				headers.setContentType(MediaType.MULTIPART_MIXED)
			).build();
	}
}
