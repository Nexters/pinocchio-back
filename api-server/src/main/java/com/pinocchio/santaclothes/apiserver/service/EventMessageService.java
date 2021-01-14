package com.pinocchio.santaclothes.apiserver.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.entity.CaptureEvent;
import com.pinocchio.santaclothes.apiserver.repository.CaptureEventRepository;
import com.pinocchio.santaclothes.common.message.CaptureEventMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventMessageService {
	private int test = 1;
	private final CaptureEventRepository captureEventRepository;

	public void captureEvent(CaptureEventMessage message) {
		if (++test < 3) {
			throw new RuntimeException("test");
		}
		CaptureEvent captureEvent = new CaptureEvent(message.getCaptureId(), message.getImageId());
		captureEventRepository.save(captureEvent);
	}
}
