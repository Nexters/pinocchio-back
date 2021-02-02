package com.pinocchio.santaclothes.apiserver.assembler;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.assembler.domain.MainView;
import com.pinocchio.santaclothes.apiserver.assembler.domain.MainView.NoticeResponse;
import com.pinocchio.santaclothes.apiserver.service.GlobalCountService;
import com.pinocchio.santaclothes.apiserver.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewAssembler {
	private final GlobalCountService globalCountService;
	private final NoticeService noticeService;

	public MainView assembleMain() {
		long count = globalCountService.getCount();
		List<NoticeResponse> noticeResponseList = noticeService.findAllNotice().stream()
			.map(
				it -> NoticeResponse.builder()
					.title(it.getTitle())
					.description(it.getDescription())
					.content(it.getContent())
					.build()
			)
			.collect(toList());

		return MainView.builder()
			.globalCount(count)
			.notices(noticeResponseList)
			.build();
	}
}
