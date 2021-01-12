package com.pinocchio.santaclothes.imageserver.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pinocchio.santaclothes.imageserver.controller.dto.ImageRequest;
import com.pinocchio.santaclothes.imageserver.service.CaptureEventService;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {
	private final CaptureEventService captureEventService;

	@GetMapping("/upload")
	public String uploadTest() {
		return "test";
	}

	@PostMapping("/upload")
	@ResponseStatus(HttpStatus.OK)
	public void upload(ImageRequest request) {
		String imageId = UUID.randomUUID().toString();
		String captureId = UUID.randomUUID().toString();
		CaptureImageRequest captureImageRequest = CaptureImageRequest.builder()
			.imageId(imageId)
			.captureId(captureId)
			.image(request.getUploadFile())
			.build();
		captureEventService.saveImage(captureImageRequest);
	}
}
