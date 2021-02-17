package com.pinocchio.santaclothes.imageserver.controller;

import static java.util.stream.Collectors.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pinocchio.santaclothes.imageserver.controller.dto.ImageDataResponse;
import com.pinocchio.santaclothes.imageserver.controller.dto.ImageDataResponse.ImageData;
import com.pinocchio.santaclothes.imageserver.service.CaptureEventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(tags = "Internal")
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class InternalController {
	private final CaptureEventService captureEventService;

	@ApiOperation("유저가 가지고 있는 이미지 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "업로드 성공"),
		@ApiResponse(code = 400, message = "요청 파라미터 오류"),
	})
	@GetMapping("/user/{userId}/image")
	public ImageDataResponse findImagesByCategory(@PathVariable String userId) {
		return captureEventService.findByUserId(userId).stream()
			.map(it -> ImageData.builder()
				.imageId(it.getImageId())
				.userId(it.getUserId())
				.clothesCategory(it.getClothesCategory())
				.filePath(it.getFilePath())
				.build()
			)
			.collect(
				collectingAndThen(toList(), ImageDataResponse::new)
			);
	}
}
