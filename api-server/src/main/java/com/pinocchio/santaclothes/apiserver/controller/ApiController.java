package com.pinocchio.santaclothes.apiserver.controller;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pinocchio.santaclothes.apiserver.dto.UploadRequestDto;
import com.pinocchio.santaclothes.apiserver.dto.UploadResponseDto;

@RestController
public class ApiController {

	@PostMapping(value = "/upload")
	@ResponseStatus(HttpStatus.CREATED)
	public UploadResponseDto upload(UploadRequestDto requestDto) { // 요청받은 데이터를 UploadRequestDto에 저장

		String userId = requestDto.getUserId();
		Instant uploadDateTime = requestDto.getUploadDateTime();
		MultipartFile uploadFile = requestDto.getUploadFile();

		String originalFileName = uploadFile.getOriginalFilename();
		File dest = new File("C:/Image/" + originalFileName);
		try {
			uploadFile.transferTo(dest); //업로드한 파일 데이터를 지정한 file에 저장
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new UploadResponseDto(userId, uploadDateTime, uploadFile);
	}

}

