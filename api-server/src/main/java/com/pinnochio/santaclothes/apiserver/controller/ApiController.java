package com.pinnochio.santaclothes.apiserver.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pinnochio.santaclothes.apiserver.dto.UploadRequestDto;
import com.pinnochio.santaclothes.apiserver.dto.UploadResponseDto;

@RestController
public class ApiController {
	@PostMapping("/upload")
	public UploadResponseDto upload(@RequestBody(required = false) UploadRequestDto requestDto) {
		return new UploadResponseDto("success", "Upload Success");
	}
}
