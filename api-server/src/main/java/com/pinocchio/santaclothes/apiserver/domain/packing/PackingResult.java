package com.pinocchio.santaclothes.apiserver.domain.packing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pinocchio.santaclothes.apiserver.entity.Ingredient;
import com.pinocchio.santaclothes.apiserver.entity.type.BleachType;
import com.pinocchio.santaclothes.apiserver.entity.type.ClothesColor;
import com.pinocchio.santaclothes.apiserver.entity.type.DryCleaning;
import com.pinocchio.santaclothes.apiserver.entity.type.DryType;
import com.pinocchio.santaclothes.apiserver.entity.type.IroningType;
import com.pinocchio.santaclothes.apiserver.entity.type.WaterType;
import com.pinocchio.santaclothes.common.type.ClothesType;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PackingResult {
	@Builder.Default
	Set<List<PackingCloth>> packingResult = new HashSet<>();

	int totalCount;

	@Value
	public static class PackingCloth {
		String clothId;

		List<Ingredient> ingredientList;

		WaterType waterType;

		BleachType bleachType;

		IroningType ironingType;

		DryType dryType;

		DryCleaning dryCleaning;

		ClothesType clothesType;

		ClothesColor clothesColor;
	}
}
