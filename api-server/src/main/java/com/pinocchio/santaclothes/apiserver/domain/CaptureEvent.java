package com.pinocchio.santaclothes.apiserver.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.springframework.lang.Nullable;

import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class CaptureEvent {
	@Id
	private String eventId;

	private String imageId;

	@Nullable
	private String result;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private CaptureEventStatus status;

	public CaptureEvent() {
		this.status = CaptureEventStatus.START;
	}

	@Builder
	public CaptureEvent(String eventId,
		String imageId,
		@Nullable String result,
		CaptureEventStatus status) {
		this.eventId = eventId;
		this.imageId = imageId;
		this.result = result;
		this.status = status;
	}
}
