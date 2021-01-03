package com.pinnochio.santaclothes.apiserver.dto;

import java.time.Instant;

import lombok.Value;

@Value
public class UploadRequestDto {
	String userId;

	Instant uploadDateTime;

}
