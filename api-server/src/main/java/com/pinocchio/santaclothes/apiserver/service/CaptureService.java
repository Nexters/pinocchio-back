package com.pinocchio.santaclothes.apiserver.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.domain.CaptureEvent;
import com.pinocchio.santaclothes.apiserver.exception.EventResumeException;
import com.pinocchio.santaclothes.apiserver.repository.CaptureEventRepository;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventMessageDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventUpdateDto;
import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaptureService {
	private final CaptureEventRepository captureEventRepository;
	private final EventMessagePublishService messagePublishService;

	public CaptureEvent findById(String eventId) {
		return captureEventRepository.findById(eventId).orElseThrow();
	}

	public List<CaptureEvent> findByStatus(CaptureEventStatus status){
		return captureEventRepository.findByStatus(status);
	}

	public void save(CaptureEvent event) {
		if(event.getStatus() == null){
			event.setStatus(CaptureEventStatus.START);
		}
		captureEventRepository.save(event);
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

			CaptureEventMessageDto messageDto = CaptureEventMessageDto.builder()
				.eventId(eventId)
				.imageId(event.getImageId())
				.status(nowStatus)
				.build();

			CaptureEventStatus updatedStatus = updateDto.getStatus();
			if (!updatedStatus.isAfter(nowStatus)) {
				return event;
			}

			switch (updatedStatus) {
				case START:
					// 업로드 완료
					messagePublishService.extract(messageDto);
					break;
				case EXTRACT:
					// 데이터 추출 완료
					messagePublishService.done(messageDto);
					break;
				case DONE:
					// 끝
					break;
			}
			return event;
		} catch (NoSuchElementException e) {
			throw new EventResumeException(e)
				.with("eventId", eventId);
		}
	}
}
