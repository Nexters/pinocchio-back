package com.pinocchio.santaclothes.apiserver.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Slf4j
public class CaptureEvent {
	@Id
	private String captureId;

	private String imageId;

	public CaptureEvent(String captureId, String imageId) {
		log.info("{} {} ", captureId, imageId);
		this.captureId = captureId;
		this.imageId = imageId;
	}

	@Enumerated(EnumType.STRING)
	private CaptureEventStatus status = CaptureEventStatus.START;
}
