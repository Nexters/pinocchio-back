package com.pinocchio.santaclothes.imageserver.service.dto;

import org.springframework.web.multipart.MultipartFile;

import com.pinocchio.santaclothes.common.type.ClothesType;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CaptureImageRequest {
	String eventId;

	String imageId;

	String userId;

	ClothesType clothesCategory;

	MultipartFile image;
}
