package com.pinocchio.santaclothes.imageserver.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.pinocchio.santaclothes.common.type.ClothesType;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CaptureImageResponse {
	String imageId;

	String userId;

	String eventId;

	String fileName;

	String filePath;

	@JsonFormat(shape = Shape.STRING)
	ClothesType category; // response 값을 궅이 Enum으로 맞춰줘야 하는 이유?

	long fileLength;
}
