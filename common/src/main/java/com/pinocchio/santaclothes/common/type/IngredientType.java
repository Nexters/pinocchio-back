package com.pinocchio.santaclothes.common.type;

import lombok.Value;

@Value
public class IngredientType implements Comparable<IngredientType> {
	String name;

	int percentage;

	@Override
	public int compareTo(IngredientType o) {
		return Integer.compare(o.percentage, this.percentage);
	}
}
