package com.pinocchio.santaclothes.imageserver.controller;

import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pinocchio.santaclothes.common.type.ClothesType;
import com.pinocchio.santaclothes.imageserver.controller.dto.ImageIdResponse;
import com.pinocchio.santaclothes.imageserver.service.CaptureEventService;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageRequest;
import com.pinocchio.santaclothes.imageserver.service.dto.CaptureImageResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "IMAGE")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {
	private static final int BUFFER_SIZE = 1024;

	private final CaptureEventService captureEventService;

	@ApiOperation("업로드")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "업로드 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류"),
	})
	@PostMapping(value = "/user/{userId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ImageIdResponse upload(
		@PathVariable String userId,
		@ApiParam @RequestPart MultipartFile uploadFile,
		@ApiParam(name = "category", example = "TOP, PANTS, SOCKS, UNDERWEAR, TOWER") @RequestParam ClothesType category
	) {
		String imageId = captureEventService.generateImageId();
		String eventId = captureEventService.generateEventId();

		CaptureImageRequest captureImageRequest = CaptureImageRequest.builder()
			.imageId(imageId)
			.eventId(eventId)
			.userId(userId)
			.clothesCategory(category)
			.image(uploadFile)
			.build();
		captureEventService.saveImage(captureImageRequest);

		return new ImageIdResponse(eventId, imageId);
	}

	@ApiOperation("다운로드")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "다운로드 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류"),
	})
	@GetMapping("/user/{userId}/image/{imageId}")
	@ResponseStatus(HttpStatus.OK)
	public void download(
		@PathVariable String userId,
		@PathVariable String imageId,
		HttpServletResponse response
	) {
		CaptureImageResponse captureImageResponse = captureEventService.findById(imageId);
		String fileName = captureImageResponse.getFileName();
		long fileLength = captureImageResponse.getFileLength();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Length", "" + fileLength);
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		try (
			FileInputStream fis = new FileInputStream(captureImageResponse.getFilePath());
			OutputStream out = response.getOutputStream()
		) {
			int readCount;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((readCount = fis.read(buffer)) != -1) {
				out.write(buffer, 0, readCount);
			}
		} catch (Exception ex) {
			throw new RuntimeException("file Load Error");
		}
	}
}
