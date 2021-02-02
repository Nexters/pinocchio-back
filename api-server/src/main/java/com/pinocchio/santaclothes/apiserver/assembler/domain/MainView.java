package com.pinocchio.santaclothes.apiserver.assembler.domain;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MainView {
	String nickName;

	long globalCount;

	List<NoticeResponse> notices;

	@Value
	@Builder
	public static class NoticeResponse{
		String title;

		String description;

		String content;
	}
}
