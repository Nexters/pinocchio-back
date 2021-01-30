package com.pinocchio.santaclothes.apiserver.domain;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
public class UserAuth {
	@Id
	String id;

	String userId;

	String authToken;

	String refreshToken;

	Instant expiredDate;

	Instant createdDate;

	public UserAuth() {
	}
}
