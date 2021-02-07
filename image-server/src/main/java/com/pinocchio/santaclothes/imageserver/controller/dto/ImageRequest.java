package com.pinocchio.santaclothes.imageserver.controller.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "업로드 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ImageRequest {
	@ApiModelProperty(value = "유저 아이디", required = true)
	private String userId;

	@ApiModelProperty(value = "이미지", required = true)
	private MultipartFile uploadFile;
}
