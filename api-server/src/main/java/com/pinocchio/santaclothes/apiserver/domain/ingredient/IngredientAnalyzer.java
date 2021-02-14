package com.pinocchio.santaclothes.apiserver.domain.ingredient;

import java.util.List;

import com.pinocchio.santaclothes.apiserver.entity.type.BleachType;
import com.pinocchio.santaclothes.apiserver.entity.type.DryCleaning;
import com.pinocchio.santaclothes.apiserver.entity.type.DryType;
import com.pinocchio.santaclothes.apiserver.entity.type.IroningType;
import com.pinocchio.santaclothes.apiserver.entity.type.WaterType;
import com.pinocchio.santaclothes.common.type.IngredientType;

public class IngredientAnalyzer {
	// TODO: 옷 함량 + 라벨을 분석하여 올바른 라벨을 반환한다
	public IngredientAnalyzeResult analyze(IngredientSource ingredientSource) {
		String title = "폴리는 물세탁이 가장 좋은 섬유입니다.";
		String description = "폴리 특성상 마찰에 의한 보풀이 잘 일어날 수 있는 섬유이기 때문에 손세탁으로 세탁하시면 좋습니다. 세제는 가능하면 중성세제나 약산성 전용세제를 사용하시고, 세탁은 무조건 실온물을 사용하시면 안전합니다.";
		BleachType bleachTypeResult = ingredientSource.getBleachType();
		DryCleaning dryCleaningResult = ingredientSource.getDryCleaning();
		DryType dryTypeResult = ingredientSource.getDryType();
		IroningType ironingTypeResult = ingredientSource.getIroningType();
		WaterType waterTypeResult = ingredientSource.getWaterType();
		List<IngredientType> ingredients = ingredientSource.getIngredients();

		return IngredientAnalyzeResult.builder()
			.title(title)
			.description(description)
			.bleachType(bleachTypeResult)
			.dryCleaning(dryCleaningResult)
			.dryType(dryTypeResult)
			.ironingType(ironingTypeResult)
			.waterType(waterTypeResult)
			.ingredients(ingredients)
			.build();
	}
}
