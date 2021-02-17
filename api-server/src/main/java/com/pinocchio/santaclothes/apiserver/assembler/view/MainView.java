package com.pinocchio.santaclothes.apiserver.assembler.view;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@ApiModel(description = "메인 화면 뷰")
@Value
@Builder
public class MainView {
	@ApiModelProperty(value = "닉네임", required = true)
	String nickName;

	@ApiModelProperty(value = "전체 라벨 인식 회수", required = true)
	long globalCount;

	@ApiModelProperty(value = "공지사항 리스트", required = true)
	List<NoticeView> notices;
}
