package com.pinocchio.santaclothes.apiserver.entity;

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
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@Slf4j
public class CaptureEvent {
	@Id
	private String eventId;

	private String imageId;

	@Nullable
	private String result;

	@Enumerated(EnumType.STRING)
	private CaptureEventStatus status = CaptureEventStatus.START;
}
