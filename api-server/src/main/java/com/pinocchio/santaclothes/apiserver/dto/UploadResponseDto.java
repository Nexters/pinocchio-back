package com.pinocchio.santaclothes.apiserver.dto;

import java.time.Instant;
import org.springframework.web.multipart.MultipartFile;
import lombok.Value;

@Value
public class UploadResponseDto {
	String userId;

	Instant uploadDateTime;

	MultipartFile uploadFile;

}
