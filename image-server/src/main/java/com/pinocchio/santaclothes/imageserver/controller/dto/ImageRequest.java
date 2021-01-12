package com.pinocchio.santaclothes.imageserver.controller.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ImageRequest {
	private String userId;

	private MultipartFile uploadFile;
}
