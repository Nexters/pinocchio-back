package com.pinocchio.santaclothes.apiserver.dto;

import java.time.Instant;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadRequestDto {

	String userId;

	Instant uploadDateTime = Instant.now();

	MultipartFile uploadFile;

}
