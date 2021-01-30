package com.pinocchio.santaclothes.apiserver.domain;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
public class UserAuth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// User의 id
	private String userId;

	private String authToken;

	private String refreshToken;

	private Instant expireDate;

	@Builder.Default
	private Instant createdDate = Instant.now();

	public UserAuth() {
	}

	public boolean isExpiredWhen(Instant time) {
		return expireDate.isBefore(time);
	}
}
