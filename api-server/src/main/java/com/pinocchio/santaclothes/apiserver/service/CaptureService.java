package com.pinocchio.santaclothes.apiserver.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.entity.CaptureEvent;
import com.pinocchio.santaclothes.apiserver.exception.EventInvalidException;
import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.repository.CaptureEventRepository;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventSaveDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventUpdateDto;
import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaptureService {
	private final CaptureEventRepository captureEventRepository;
	// private final EventMessagePublishService messagePublishService;

	public CaptureEvent findById(String eventId) {
		return captureEventRepository.findById(eventId).orElseThrow();
	}

	public List<CaptureEvent> findByStatus(CaptureEventStatus status) {
		return captureEventRepository.findByStatus(status);
	}

	public void save(CaptureEventSaveDto saveDto) {
		CaptureEvent captureEvent = CaptureEvent.builder()
			.eventId(saveDto.getEventId())
			.imageId(saveDto.getImageId())
			.userId(saveDto.getUserId())
			.status(saveDto.getStatus())
			.build();
		captureEventRepository.save(captureEvent);
	}

	public CaptureEvent update(CaptureEventUpdateDto updateDto) {
		String eventId = updateDto.getEventId();
		try {
			CaptureEvent event = findById(eventId);
			CaptureEventStatus nowStatus = event.getStatus();
			String updatedImageId = updateDto.getImageId();
			if (updatedImageId != null) {
				event.setImageId(updatedImageId);
			}

			// CaptureEventMessageDto messageDto = CaptureEventMessageDto.builder()
			// 	.eventId(eventId)
			// 	.imageId(event.getImageId())
			// 	.status(nowStatus)
			// 	.build();

			CaptureEventStatus toUpdateStatus = updateDto.getStatus();
			if (!toUpdateStatus.isAfter(nowStatus)) {
				return event;
			}

			switch (toUpdateStatus) {
				case START:
					// TODO: Phase 2
					// messagePublishService.extract(messageDto);
					break;
				case EXTRACT:
					event.setStatus(CaptureEventStatus.EXTRACT);
					// TODO: Phase 2
					// messagePublishService.done(messageDto);
					break;
				case DONE:
					event.setStatus(CaptureEventStatus.DONE);
					// 끝
					break;
			}
			return event;
		} catch (NoSuchElementException e) {
			throw new EventInvalidException(e, eventId, ExceptionReason.EVENT_NOT_EXIST);
		}
	}
}
