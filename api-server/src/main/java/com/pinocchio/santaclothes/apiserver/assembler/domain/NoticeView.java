package com.pinocchio.santaclothes.apiserver.assembler.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@ApiModel(description = "공지사항 뷰")
@Value
@Builder
public class NoticeView {
	@ApiModelProperty(value = "제목", required = true)
	String title;

	@ApiModelProperty(value = "설명", required = true)
	String description;

	@ApiModelProperty(value = "내용", required = true)
	String content;
}
