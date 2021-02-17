package com.pinocchio.santaclothes.imageserver.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.pinocchio.santaclothes.common.type.ClothesType;

import lombok.Builder;
import lombok.Value;

@Value
public class ImageDataResponse {
	List<ImageData> imageDataList;

	@Builder
	public static class ImageData {
		String imageId;

		String savedFileName;

		String userId;

		@JsonFormat(shape = Shape.STRING)
		ClothesType clothesCategory;

		String filePath;
	}
}
