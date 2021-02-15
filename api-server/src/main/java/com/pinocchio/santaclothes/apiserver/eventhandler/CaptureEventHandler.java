package com.pinocchio.santaclothes.apiserver.eventhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.pinocchio.santaclothes.apiserver.entity.Cloth;
import com.pinocchio.santaclothes.apiserver.entity.Ingredient;
import com.pinocchio.santaclothes.apiserver.entity.event.CreateClothEvent;
import com.pinocchio.santaclothes.apiserver.exception.EventInvalidException;
import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.service.CaptureService;
import com.pinocchio.santaclothes.apiserver.service.ClothService;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventResultDto;
import com.pinocchio.santaclothes.common.utils.Uuids;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CaptureEventHandler {
	private final ClothService clothService;
	private final CaptureService captureService;

	@Async
	@TransactionalEventListener // transaction 이후에 이벤트 처리
	public void createCloth(CreateClothEvent event) {
		CaptureEventDto eventDto = captureService.findById(event.getEventId());
		CaptureEventResultDto resultDto = eventDto.getResult();

		if (resultDto == null) {
			throw new EventInvalidException(event.getEventId(), ExceptionReason.EVENT_NO_RESULT);
		}

		List<Ingredient> ingredientList = resultDto.getIngredientList().stream()
			.map(it -> new Ingredient(it.getName(), it.getPercentage()))
			.collect(Collectors.toList());

		String clothId = Uuids.generateUuidString();

		Cloth cloth = Cloth.builder()
			.clothId(clothId)
			.eventId(eventDto.getEventId())
			.imageId(eventDto.getImageId())
			.userId(eventDto.getUserId())
			.clothesColor(resultDto.getClothesColor())
			.waterType(resultDto.getWaterType())
			.bleachType(resultDto.getBleachType())
			.dryCleaning(resultDto.getDryCleaning())
			.dryType(resultDto.getDryType())
			.ironingType(resultDto.getIroningType())
			.ingredientList(ingredientList)
			.build();
		ingredientList.forEach(it -> it.setCloth(cloth)); // TODO: 우아하게 처리하기
		clothService.save(cloth);
	}
}
