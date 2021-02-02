package com.pinocchio.santaclothes.imageserver.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pinocchio.santaclothes.imageserver.controller.dto.ImageRequest;
import com.pinocchio.santaclothes.imageserver.controller.dto.ImageResponse;
import com.pinocchio.santaclothes.imageserver.service.CaptureEventService;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageRequest;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {
	private final CaptureEventService captureEventService;

	@PostMapping("/image")
	@ResponseStatus(HttpStatus.OK)
	public ImageResponse upload(ImageRequest request) {
		String imageId = UUID.randomUUID().toString();
		String eventId = UUID.randomUUID().toString();
		CaptureImageRequest captureImageRequest = CaptureImageRequest.builder()
			.imageId(imageId)
			.eventId(eventId)
			.image(request.getUploadFile())
			.build();
		captureEventService.saveImage(captureImageRequest);
		return new ImageResponse(eventId, imageId);
	}

	@GetMapping("/image/{imageId}")
	@ResponseStatus(HttpStatus.OK)
	public void download(@PathVariable("imageId") String imageId, HttpServletResponse response) {
		CaptureImageResponse captureImageResponse = captureEventService.findById(imageId);
		response.setHeader("Content-Disposition",
			"attachment; filename=\"" + captureImageResponse.getFileName() + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Length", "" + captureImageResponse.getFileLength());
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		try (
			FileInputStream fis = new FileInputStream(captureImageResponse.getFilePath());
			OutputStream out = response.getOutputStream()
		) {
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while ((readCount = fis.read(buffer)) != -1) {
				out.write(buffer, 0, readCount);
			}
		} catch (Exception ex) {
			throw new RuntimeException("file Load Error");
		}
	}
}
