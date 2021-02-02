package com.pinocchio.santaclothes.imageserver.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CaptureImageResponse {
	String imageId;

	String fileName;

	String filePath;

	long fileLength;
}
