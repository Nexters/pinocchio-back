package com.pinocchio.santaclothes.consumer.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.common.message.CaptureEventCreateMessage;
import com.pinocchio.santaclothes.common.message.CaptureEventProcessDoneMessage;
import com.pinocchio.santaclothes.common.message.CaptureEventProcessRequestMessage;
import com.pinocchio.santaclothes.consumer.apiclient.apiserver.ApiServerApiClient;
import com.pinocchio.santaclothes.consumer.apiclient.apiserver.dto.CreateEventRequest;
import com.pinocchio.santaclothes.consumer.apiclient.apiserver.dto.FinishEventRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CaptureEventService {
	private final ApiServerApiClient apiClient;

	public void create(CaptureEventCreateMessage message) {
		apiClient.createEvent(new CreateEventRequest(message.getEventId(), message.getImageId())).block();
	}

	public void processRequest(CaptureEventProcessRequestMessage message) {
		// Todo process 서버에 요청 전송
	}

	public void processDone(CaptureEventProcessDoneMessage message) {
		FinishEventRequest request = FinishEventRequest.builder()
			.eventId(message.getEventId())
			.imageId(message.getImageId())
			.result(message.getResult())
			.build();

		apiClient.finishEvent(request).block();
	}
}
