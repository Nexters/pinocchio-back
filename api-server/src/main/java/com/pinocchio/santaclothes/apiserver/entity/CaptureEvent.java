package com.pinocchio.santaclothes.apiserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;

import com.pinocchio.santaclothes.apiserver.entity.event.CreateClothEvent;
import com.pinocchio.santaclothes.common.type.CaptureEventStatus;

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

	public void setStatus(CaptureEventStatus toUpdateStatus) {
		if (toUpdateStatus.isAfter(status)) {
			switch (toUpdateStatus) {
				case START:
					break;
				case EXTRACT:
					this.status = CaptureEventStatus.EXTRACT;
					break;
				case REPORT:
					this.status = CaptureEventStatus.REPORT;
					break;
				case DONE:
					this.status = CaptureEventStatus.DONE;
					break;
			}
		}
	}

	public void done(ApplicationEventPublisher publisher) {
		publisher.publishEvent(new CreateClothEvent(eventId));
	}
}
