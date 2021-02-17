package com.pinocchio.santaclothes.apiserver.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinocchio.santaclothes.apiserver.entity.CaptureEvent;
import com.pinocchio.santaclothes.apiserver.exception.EventInvalidException;
import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.repository.CaptureEventRepository;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventResultDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventSaveRequestDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventUpdateRequestDto;
import com.pinocchio.santaclothes.apiserver.support.ObjectSupports;
import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CaptureService {
	private final CaptureEventRepository captureEventRepository;
	private final ObjectMapper objectMapper;
	private final ApplicationEventPublisher publisher;

	public CaptureEventDto findById(String eventId) {
		CaptureEvent captureEvent = captureEventRepository.findById(eventId).orElseThrow();
		CaptureEventResultDto resultDto = getResultDto(captureEvent.getResult());
		return CaptureEventDto.builder()
			.eventId(captureEvent.getEventId())
			.imageId(captureEvent.getImageId())
			.userId(captureEvent.getUserId())
			.result(resultDto)
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
				.result(getResultDto(it.getResult()))
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
			String updatedImageId = updateDto.getImageId();
			String toResult = updateDto.getResult();
			CaptureEventStatus toUpdateStatus = updateDto.getStatus();

			ObjectSupports.ifNotNullAccept(updatedImageId, event::setImageId);
			ObjectSupports.ifNotNullAccept(toResult, event::setResult);
			ObjectSupports.ifNotNullAccept(toUpdateStatus, event::setStatus);

			if (CaptureEventStatus.DONE == toUpdateStatus) {
				event.done(publisher);
			}

			return CaptureEventDto.builder()
				.eventId(event.getEventId())
				.imageId(event.getImageId())
				.status(event.getStatus())
				.result(getResultDto(event.getResult()))
				.build();

		} catch (NoSuchElementException e) {
			throw new EventInvalidException(e, eventId, ExceptionReason.EVENT_NOT_EXIST);
		}
	}

	@Nullable
	private CaptureEventResultDto getResultDto(String result) {
		return ObjectSupports.ifNotNullApply(result, it -> {
			try {
				return objectMapper.readValue(it, CaptureEventResultDto.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		});
	}
}
