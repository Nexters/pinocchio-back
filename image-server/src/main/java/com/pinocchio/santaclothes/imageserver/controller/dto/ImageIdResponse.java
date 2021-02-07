package com.pinocchio.santaclothes.imageserver.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel(description = "업로드 응답")
@Value
public class ImageIdResponse {
	@ApiModelProperty(value = "이벤트 아이디", required = true)
	String eventId;

	@ApiModelProperty(value = "이미지 아이디", required = true)
	String imageId;
}
