package com.pinnochio.santaclothes.apiserver.dto;

import com.pinnochio.santaclothes.apiserver.type.ApiResponseStatus;

import lombok.Value;

@Value
public class UploadResponseDto {
	ApiResponseStatus status;

	String message;
}
