package com.pinocchio.santaclothes.apiserver.controller.converter;

import javax.annotation.Nullable;

import com.pinocchio.santaclothes.apiserver.controller.dto.LabelResult;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventResultDto;

public class CaptureEventConverter {
	public static LabelResult toLabelResult(@Nullable CaptureEventResultDto resultDto) {
		if (resultDto == null) {
			return null;
		}

		return LabelResult.builder()
			.bleachType(resultDto.getBleachType())
			.dryCleaning(resultDto.getDryCleaning())
			.dryType(resultDto.getDryType())
			.ingredientList(resultDto.getIngredientList())
			.ironingType(resultDto.getIroningType())
			.waterType(resultDto.getWaterType())
			.clothesColor(resultDto.getClothesColor())
			.build();
	}
}
