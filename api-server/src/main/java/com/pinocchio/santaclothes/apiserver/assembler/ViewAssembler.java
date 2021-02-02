package com.pinocchio.santaclothes.apiserver.assembler;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.assembler.domain.MainView;
import com.pinocchio.santaclothes.apiserver.assembler.domain.MainView.NoticeResponse;
import com.pinocchio.santaclothes.apiserver.entity.User;
import com.pinocchio.santaclothes.apiserver.service.GlobalCountService;
import com.pinocchio.santaclothes.apiserver.service.NoticeService;
import com.pinocchio.santaclothes.apiserver.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewAssembler {
	private final GlobalCountService globalCountService;
	private final NoticeService noticeService;
	private final UserService userService;

	public MainView assembleMain(String accessToken) {
		User user = userService.findByAccessToken(accessToken);

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
		String nickName = user.getNickName();

		return MainView.builder()
			.nickName(nickName)
			.globalCount(count)
			.notices(noticeResponseList)
			.build();
	}
}