package com.pinocchio.santaclothes.apiserver.service.dto;

import java.util.ArrayList;
import java.util.List;

import com.pinocchio.santaclothes.apiserver.entity.type.BleachType;
import com.pinocchio.santaclothes.apiserver.entity.type.DryCleaning;
import com.pinocchio.santaclothes.apiserver.entity.type.DryType;
import com.pinocchio.santaclothes.apiserver.entity.type.IroningType;
import com.pinocchio.santaclothes.apiserver.entity.type.WaterType;
import com.pinocchio.santaclothes.apiserver.entity.type.ClothesColor;
import com.pinocchio.santaclothes.common.type.IngredientType;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CaptureEventResultDto {
	@Builder.Default
	List<IngredientType> ingredientList = new ArrayList<>();

	WaterType waterType;

	BleachType bleachType;

	DryType dryType;

	IroningType ironingType;

	DryCleaning dryCleaning;

	ClothesColor clothesColor;
}
