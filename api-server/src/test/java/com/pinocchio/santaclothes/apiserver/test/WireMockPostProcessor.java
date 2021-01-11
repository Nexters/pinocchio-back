package com.pinocchio.santaclothes.apiserver.test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@Configuration
public class WireMockPostProcessor implements EnvironmentPostProcessor, InitializingBean {
	private static final WireMock WIRE_MOCK;
	private static final WireMockServer WIRE_MOCK_SERVER;
	private static final Logger LOG = LoggerFactory.getLogger(WireMockPostProcessor.class);

	static {
		int defaultPort = 10000;
		Random random = new Random();
		int port = defaultPort + random.nextInt(3001);
		int max = port + 1000;

		WireMockServer wireMockServer = null;
		while (port < max) {
			try {
				wireMockServer = new WireMockServer(
					options()
						.port(port)
				);
				wireMockServer.start();
				break;
			} catch (Exception e) {
				LOG.info("WireMockServer start failed. port : {}", port);
			}
			port++;
		}

		if (wireMockServer == null || !wireMockServer.isRunning()) {
			throw new RuntimeException("wireMock is not running");
		}

		WIRE_MOCK_SERVER = wireMockServer;
		WIRE_MOCK = new WireMock(WIRE_MOCK_SERVER);

		Runtime.getRuntime().addShutdownHook(
			new Thread(() -> {
				try {
					WIRE_MOCK_SERVER.stop();
				} catch (Exception e) {
					// ignore
				}
			})
		);
	}

	@Override
	public void afterPropertiesSet() {
		System.setProperty("wiremock.server.port", String.valueOf(WIRE_MOCK_SERVER.port()));
	}

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		WireMock.configureFor(WIRE_MOCK);
	}
}
