package com.pinnochio.santaclothes.common.utils;

import java.util.Random;
import java.util.UUID;

public class RandomUtils {
	private static final Random RANDOM = new Random();

	private RandomUtils() {
	}

	public int randomInteger(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("min 값이 max보다 클 수 없습니다.");
		}
		return RANDOM.nextInt(max - min + 1) + min;
	}

	public synchronized String randomUUID() {
		return UUID.randomUUID().toString();
	}
}
