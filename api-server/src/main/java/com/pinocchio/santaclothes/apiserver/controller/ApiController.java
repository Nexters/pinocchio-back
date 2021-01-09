package com.pinocchio.santaclothes.apiserver.controller;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pinocchio.santaclothes.apiserver.dto.UploadRequestDto;
import com.pinocchio.santaclothes.apiserver.dto.UploadResponseDto;

@RestController
public class ApiController {

	@GetMapping("/healthCheck")
	public String healthCheck(){
		return "ok";
	}

	@PostMapping(value = "/upload")
	@ResponseStatus(HttpStatus.CREATED)
	public UploadResponseDto upload(UploadRequestDto requestDto) { // 요청받은 데이터를 UploadRequestDto에 저장

		String userId = requestDto.getUserId();
		Instant uploadDateTime = requestDto.getUploadDateTime();
		MultipartFile uploadFile = requestDto.getUploadFile();


		return new UploadResponseDto(userId, uploadDateTime, uploadFile);
	}

}


