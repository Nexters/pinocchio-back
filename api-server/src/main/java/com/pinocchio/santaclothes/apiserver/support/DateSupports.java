package com.pinocchio.santaclothes.apiserver.support;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateSupports {
	private static final int EXPIRE_AMOUNT = 1;
	private static final TemporalUnit EXPIRE_UNIT = ChronoUnit.DAYS;

	public static Instant calculateExpireDate(Instant now) {
		return now.plus(EXPIRE_AMOUNT, EXPIRE_UNIT);
	}
}
