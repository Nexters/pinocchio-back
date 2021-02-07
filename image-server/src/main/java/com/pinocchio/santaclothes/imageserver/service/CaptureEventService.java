package com.pinocchio.santaclothes.imageserver.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pinocchio.santaclothes.common.utils.Uuids;
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
			.eventId(captureImage.getEventId())
			.filePath(captureImage.getFilePath())
			.userId(captureImage.getUserId())
			.fileLength(captureImage.getFileLength())
			.fileName(captureImage.getSavedFileName())
			.category(captureImage.getClothesCategory())
			.build();
	}

	public List<CaptureImage> findByUserId(String userId) {
		return captureImageRepository.findByUserId(userId);
	}

	public String generateImageId() {
		return Uuids.generateUuidString();
	}

	public String generateEventId() {
		return Uuids.generateUuidString();
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void saveImage(CaptureImageRequest request) {
		try {
			MultipartFile file = request.getImage();
			new File(FILE_PREFIX_URL).mkdirs();

			String fileName = UUID.randomUUID().toString();
			String filePath = FILE_PREFIX_URL + "/" + fileName + ".png";
			File transferFile = new File(filePath);
			file.transferTo(transferFile);

			String imageId = request.getImageId();
			String eventId = request.getEventId();
			String userId = request.getUserId();
			String originalFileName = file.getOriginalFilename();
			long fileLength = file.getSize();

			CaptureImage captureImage = CaptureImage.builder()
				.imageId(imageId)
				.eventId(eventId)
				.userId(userId)
				.originalFileName(originalFileName)
				.savedFileName(fileName)
				.fileLength(fileLength)
				.filePath(filePath)
				.clothesCategory(request.getClothesCategory())
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
