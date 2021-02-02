package com.pinocchio.santaclothes.imageserver.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ObjectUtils;

public class PathPostProcessor implements EnvironmentPostProcessor {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		String systemImageProperty = System.getProperty("PINOCCHIO_IMAGES_PATH");
		String springEnvironmentProperty = environment.getProperty("pinocchio.images.path");
		String javaTempPath = System.getProperty("java.io.tmpdir") + "/images";

		if (!ObjectUtils.isEmpty(systemImageProperty)) {
			return;
		}
		if (!ObjectUtils.isEmpty(springEnvironmentProperty)) {
			System.setProperty("PINOCCHIO_IMAGES_PATH", springEnvironmentProperty);
			return;
		}
		System.setProperty("PINOCCHIO_IMAGES_PATH", javaTempPath);
	}
}
