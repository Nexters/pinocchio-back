package com.pinocchio.santaclothes.apiserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.springframework.lang.Nullable;

import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Builder
public class CaptureEvent {
	@Id
	private String eventId;

	private String imageId;

	private String userId;

	@Nullable
	@Column(length = 65535)
	private String result;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private CaptureEventStatus status = CaptureEventStatus.START;

	public CaptureEvent() {
	}
}
