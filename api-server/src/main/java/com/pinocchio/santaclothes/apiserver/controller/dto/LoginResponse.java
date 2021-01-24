package com.pinocchio.santaclothes.apiserver.controller.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginResponse {
	String authToken;

	String refreshToken;

	Instant expireDateTime;
}
