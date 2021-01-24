package com.pinocchio.santaclothes.apiserver.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinocchio.santaclothes.apiserver.type.LoginType;

import lombok.Value;

@Value
public class LoginRequest {
	String userToken;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	LoginType loginType;
}
