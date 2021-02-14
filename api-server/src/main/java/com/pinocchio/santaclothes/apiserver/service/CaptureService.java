package com.pinocchio.santaclothes.apiserver.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.entity.CaptureEvent;
import com.pinocchio.santaclothes.apiserver.exception.EventInvalidException;
import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.repository.CaptureEventRepository;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventSaveRequestDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventUpdateRequestDto;
import com.pinocchio.santaclothes.apiserver.support.ObjectSupports;
import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CaptureService {
	private final CaptureEventRepository captureEventRepository;
	// private final EventMessagePublishService messagePublishService;

	public CaptureEventDto findById(String eventId) {
		CaptureEvent captureEvent = captureEventRepository.findById(eventId).orElseThrow();

		return CaptureEventDto.builder()
			.eventId(captureEvent.getEventId())
			.imageId(captureEvent.getImageId())
			.userId(captureEvent.getUserId())
			.result(captureEvent.getResult())
			.status(captureEvent.getStatus())
			.build();
	}

	public List<CaptureEventDto> findByStatus(CaptureEventStatus status) {
		return captureEventRepository.findByStatus(status).stream()
			.map(it -> CaptureEventDto.builder()
				.eventId(it.getEventId())
				.imageId(it.getImageId())
				.userId(it.getUserId())
				.status(it.getStatus())
				.result(it.getResult())
				.build())
			.collect(toList());
	}

	public void save(CaptureEventSaveRequestDto saveDto) {
		CaptureEvent captureEvent = CaptureEvent.builder()
			.eventId(saveDto.getEventId())
			.imageId(saveDto.getImageId())
			.userId(saveDto.getUserId())
			.status(saveDto.getStatus())
			.build();
		captureEventRepository.save(captureEvent);
	}

	public CaptureEventDto update(CaptureEventUpdateRequestDto updateDto) {
		String eventId = updateDto.getEventId();
		try {
			CaptureEvent event = captureEventRepository.findById(eventId).orElseThrow();
			CaptureEventStatus nowStatus = event.getStatus();
			String updatedImageId = updateDto.getImageId();
			String toResult = updateDto.getResult();

			ObjectSupports.ifNotNullAccept(updatedImageId, event::setImageId);
			ObjectSupports.ifNotNullAccept(toResult, event::setResult);

			CaptureEventStatus toUpdateStatus = updateDto.getStatus();
			if (toUpdateStatus.isAfter(nowStatus)) {
				switch (toUpdateStatus) {
					case START:
						break;
					case EXTRACT:
						event.setStatus(CaptureEventStatus.EXTRACT);
						break;
					case DONE:
						event.setStatus(CaptureEventStatus.DONE);
						break;
				}
			}

			return CaptureEventDto.builder()
				.eventId(event.getEventId())
				.imageId(event.getImageId())
				.status(event.getStatus())
				.result(event.getResult())
				.build();

		} catch (NoSuchElementException e) {
			throw new EventInvalidException(e, eventId, ExceptionReason.EVENT_NOT_EXIST);
		}
	}
}
