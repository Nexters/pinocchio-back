package com.pinocchio.santaclothes.apiserver.eventhandler;

import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.concurrent.Executor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SyncTaskExecutor;

import com.pinocchio.santaclothes.apiserver.entity.CaptureEvent;
import com.pinocchio.santaclothes.apiserver.entity.Cloth;
import com.pinocchio.santaclothes.apiserver.eventhandler.CaptureEventHandlerTest.ContextConfiguration;
import com.pinocchio.santaclothes.apiserver.repository.CaptureEventRepository;
import com.pinocchio.santaclothes.apiserver.service.CaptureService;
import com.pinocchio.santaclothes.apiserver.service.ClothService;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventUpdateRequestDto;
import com.pinocchio.santaclothes.apiserver.test.SpringTest;
import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

@Import(ContextConfiguration.class)
public class CaptureEventHandlerTest extends SpringTest {
	@MockBean
	private ClothService clothService;

	@Autowired
	private CaptureService captureService;

	@MockBean
	private CaptureEventRepository captureEventRepository;

	@Configuration
	static class ContextConfiguration {
		@Bean
		@Primary
		public Executor executor() {
			return new SyncTaskExecutor();
		}
	}

	@Test
	void createClothWhenEventIsDone() {
		String eventId = "eventId";
		String imageId = "imageId";
		String userId = "userId";
		CaptureEvent mockCaptureEvent = CaptureEvent.builder()
			.eventId(eventId)
			.imageId(imageId)
			.userId(userId)
			.status(CaptureEventStatus.EXTRACT)
			.result("{\n"
				+ "    \"ingredientList\": [\n"
				+ "      {\n"
				+ "        \"name\": \"string\",\n"
				+ "        \"percentage\": 0\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"waterType\": \"FORBIDDEN_BLOWER_JP\",\n"
				+ "    \"bleachType\": \"ALL_JP\",\n"
				+ "    \"ironingType\": \"FORBIDDEN_JP\",\n"
				+ "    \"dryType\": \"DRY_BLOWER_FORBIDDEN_KR\",\n"
				+ "    \"dryCleaning\": \"DRY_CLEANING_FORBIDDEN_JP\",\n"
				+ "    \"clothesColor\": \"BEIGE\"\n"
				+ "  }")
			.build();

		when(captureEventRepository.findById(eventId)).thenReturn(Optional.of(mockCaptureEvent));

		CaptureEventUpdateRequestDto requestDto = CaptureEventUpdateRequestDto.builder()
			.eventId(eventId)
			.imageId(imageId)
			.status(CaptureEventStatus.DONE)
			.build();

		captureService.update(requestDto);

		verify(clothService, times(1)).save(any(Cloth.class));
	}
}
