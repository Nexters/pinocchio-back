package com.pinocchio.santaclothes.imageserver.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pinocchio.santaclothes.common.type.CaptureEventStatus;
import com.pinocchio.santaclothes.common.utils.Uuids;
import com.pinocchio.santaclothes.imageserver.apiclient.apiserver.ApiServerApiClient;
import com.pinocchio.santaclothes.imageserver.apiclient.apiserver.dto.CreateEventRequest;
import com.pinocchio.santaclothes.imageserver.apiclient.apiserver.dto.CreateEventRequestDto;
import com.pinocchio.santaclothes.imageserver.entity.CaptureImage;
import com.pinocchio.santaclothes.imageserver.repository.CaptureImageRepository;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageRequest;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CaptureEventService {
	private static final String FILE_PREFIX_URL;
	static{
		FILE_PREFIX_URL = System.getProperty("PINOCCHIO_IMAGES_PATH");
	}
	private final CaptureImageRepository captureImageRepository;
	private final ApiServerApiClient apiServerApiClient;

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

			apiServerApiClient.createEvent(new CreateEventRequestDto(userId, eventId, imageId, CaptureEventStatus.START)).blockOptional().orElseThrow();

			captureImageRepository.save(captureImage);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
