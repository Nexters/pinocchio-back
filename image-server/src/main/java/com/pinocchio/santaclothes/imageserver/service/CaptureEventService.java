package com.pinocchio.santaclothes.imageserver.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pinocchio.santaclothes.common.message.CaptureEventMessage;
import com.pinocchio.santaclothes.imageserver.entity.CaptureImage;
import com.pinocchio.santaclothes.imageserver.repository.CaptureImageRepository;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageRequest;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Sinks;

@Service
@Transactional
@Slf4j
public class CaptureEventService {
	private static final String FILE_PREFIX_URL = "/image";
	private final Sinks.Many<CaptureEventMessage> captureEmitter;
	private final CaptureImageRepository captureImageRepository;

	public CaptureEventService(
		@Qualifier("captureEmitter") Sinks.Many<CaptureEventMessage> captureEmitter,
		CaptureImageRepository captureImageRepository
	) {
		this.captureEmitter = captureEmitter;
		this.captureImageRepository = captureImageRepository;
	}

	public void saveImage(CaptureImageRequest request) {
		MultipartFile file = request.getImage();
		String originalFileName = file.getOriginalFilename();
		String fileName = UUID.randomUUID().toString();
		String filePath = FILE_PREFIX_URL + fileName;
		CaptureImage captureImage = CaptureImage.builder()
			.imageId(request.getImageId())
			.originalFileName(originalFileName)
			.savedFileName(fileName)
			.filePath(filePath)
			.build();
		captureImageRepository.save(captureImage);
		captureEmitter.tryEmitNext(new CaptureEventMessage(request.getCaptureId(), request.getImageId()));
	}
}
