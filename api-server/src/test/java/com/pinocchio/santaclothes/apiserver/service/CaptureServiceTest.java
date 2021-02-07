package com.pinocchio.santaclothes.apiserver.service;

import static org.assertj.core.api.BDDAssertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pinocchio.santaclothes.apiserver.entity.CaptureEvent;
import com.pinocchio.santaclothes.apiserver.repository.CaptureEventRepository;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventUpdateRequestDto;
import com.pinocchio.santaclothes.apiserver.test.SpringTest;
import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

class CaptureServiceTest extends SpringTest {
	@MockBean
	private CaptureEventRepository captureEventRepository;

	@Autowired
	private CaptureService sut;

	@Test
	void updateImageId() {
		String captureEventId = "eventId";
		String imageId = "imageId";
		CaptureEventStatus status = CaptureEventStatus.START;

		Optional<CaptureEvent> captureEvent = Optional.of(
			CaptureEvent.builder()
			.eventId(captureEventId)
			.imageId(imageId)
			.status(status)
			.build()
		);

		BDDMockito.given(captureEventRepository.findById(captureEventId))
			.willReturn(captureEvent);

		String newImageId = "newImageId";
		CaptureEventUpdateRequestDto captureEventUpdateRequestDto = CaptureEventUpdateRequestDto.builder()
			.eventId(captureEventId)
			.imageId(newImageId)
			.status(status)
			.build();

		CaptureEventDto newCaptureEvent = sut.update(captureEventUpdateRequestDto);

		then(newCaptureEvent.getEventId()).isEqualTo(captureEventId);
		then(newCaptureEvent.getImageId()).isEqualTo(newImageId);
		then(newCaptureEvent.getStatus()).isEqualTo(status);
	}

	@Test
	void updateStatus() {
		String captureEventId = "eventId";
		String imageId = "imageId";
		CaptureEventStatus status = CaptureEventStatus.START;

		Optional<CaptureEvent> captureEvent = Optional.of(
			CaptureEvent.builder()
				.eventId(captureEventId)
				.imageId(imageId)
				.status(status)
				.build()
		);

		BDDMockito.given(captureEventRepository.findById(captureEventId))
			.willReturn(captureEvent);

		CaptureEventStatus newStatus = CaptureEventStatus.EXTRACT;
		CaptureEventUpdateRequestDto captureEventUpdateRequestDto = CaptureEventUpdateRequestDto.builder()
			.eventId(captureEventId)
			.imageId(imageId)
			.status(newStatus)
			.build();

		CaptureEventDto newCaptureEvent = sut.update(captureEventUpdateRequestDto);

		then(newCaptureEvent.getEventId()).isEqualTo(captureEventId);
		then(newCaptureEvent.getImageId()).isEqualTo(imageId);
		then(newCaptureEvent.getStatus()).isEqualTo(newStatus);
	}

	@Test
	void updateBeforeStatus() {
		String captureEventId = "eventId";
		String imageId = "imageId";
		CaptureEventStatus status = CaptureEventStatus.EXTRACT;

		Optional<CaptureEvent> captureEvent = Optional.of(
			CaptureEvent.builder()
				.eventId(captureEventId)
				.imageId(imageId)
				.status(status)
				.build()
		);

		BDDMockito.given(captureEventRepository.findById(captureEventId))
			.willReturn(captureEvent);

		CaptureEventStatus newStatus = CaptureEventStatus.START;
		CaptureEventUpdateRequestDto captureEventUpdateRequestDto = CaptureEventUpdateRequestDto.builder()
			.eventId(captureEventId)
			.imageId(imageId)
			.status(newStatus)
			.build();

		CaptureEventDto newCaptureEvent = sut.update(captureEventUpdateRequestDto);

		then(newCaptureEvent.getEventId()).isEqualTo(captureEventId);
		then(newCaptureEvent.getImageId()).isEqualTo(imageId);
		then(newCaptureEvent.getStatus()).isEqualTo(status);
	}
}
