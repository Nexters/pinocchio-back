package com.pinocchio.santaclothes.imageserver.service.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CaptureImageRequest {
	String captureId;

	String imageId;

	MultipartFile image;
}
