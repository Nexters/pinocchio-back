package com.pinocchio.santaclothes.imageserver.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pinocchio.santaclothes.imageserver.entity.CaptureImage;
import com.pinocchio.santaclothes.imageserver.repository.CaptureImageRepository;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageRequest;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CaptureEventService {
	private static final String FILE_PREFIX_URL;
	static{
		FILE_PREFIX_URL = System.getProperty("PINOCCHIO_IMAGES_PATH");
	}
	// private final Sinks.Many<CaptureEventCreateMessage> captureCreateEmitter;
	// private final Sinks.Many<CaptureEventProcessRequestMessage> captureProcessRequestEmitter;
	private final CaptureImageRepository captureImageRepository;

	public CaptureEventService(
		// @Qualifier("captureCreateEmitter") Sinks.Many<CaptureEventCreateMessage> captureCreateEmitter,
		// @Qualifier("captureProcessRequestEmitter") Sinks.Many<CaptureEventProcessRequestMessage> captureProcessRequestEmitter,
		CaptureImageRepository captureImageRepository
	) {
		// this.captureCreateEmitter = captureCreateEmitter;
		// this.captureProcessRequestEmitter = captureProcessRequestEmitter;
		this.captureImageRepository = captureImageRepository;
	}

	public CaptureImageResponse findById(String imageId) {
		CaptureImage captureImage = captureImageRepository.findById(imageId).orElseThrow();
		return CaptureImageResponse.builder()
			.imageId(captureImage.getImageId())
			.filePath(captureImage.getFilePath())
			.fileLength(captureImage.getFileLength())
			.fileName(captureImage.getSavedFileName())
			.build();
	}

	public void saveImage(CaptureImageRequest request) {
		try {
			MultipartFile file = request.getImage();
			String originalFileName = file.getOriginalFilename();
			String fileName = UUID.randomUUID().toString();
			System.out.println(FILE_PREFIX_URL);
			new File(FILE_PREFIX_URL).mkdirs();
			String filePath = FILE_PREFIX_URL + "/" + fileName + ".png";
			File transferFile = new File(filePath);
			file.transferTo(transferFile);
			CaptureImage captureImage = CaptureImage.builder()
				.imageId(request.getImageId())
				.originalFileName(originalFileName)
				.savedFileName(fileName)
				.fileLength(file.getSize())
				.filePath(filePath)
				.build();
			captureImageRepository.save(captureImage);
			// captureCreateEmitter.tryEmitNext(new CaptureEventCreateMessage(request.getEventId(), request.getImageId()));
			// captureProcessRequestEmitter.tryEmitNext(
			// 	new CaptureEventProcessRequestMessage(request.getEventId(), request.getImageId())
			// );
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
