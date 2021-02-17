package com.pinocchio.santaclothes.apiserver.support;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectSupports {
	public static <T> void ifNotNullAccept(T value, Consumer<T> toConsume) {
		if (value != null) {
			toConsume.accept(value);
		}
	}

	public static <T, U> U ifNotNullApply(T value, Function<T, U> toApply) {
		if (value == null) {
			return null;
		}
		return toApply.apply(value);
	}
}
