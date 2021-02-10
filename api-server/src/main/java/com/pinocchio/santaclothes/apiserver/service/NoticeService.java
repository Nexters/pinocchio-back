package com.pinocchio.santaclothes.apiserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.entity.Notice;
import com.pinocchio.santaclothes.apiserver.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	private final NoticeRepository noticeRepository;

	public List<Notice> findAllNotice(){
		return List.of(
			Notice.builder()
				.title("라벨을 찍어보세요")
				.description("라벨을 찍고 세탁법을 확인해보세요1")
				.content("라벨을 10개 찍으면 상품이 꽝!")
				.build(),

			Notice.builder()
				.title("옷 세탁법이 궁금하세요?")
				.description("카메라 버튼을 눌러 라벨을 찍어보세요")
				.build(),

			Notice.builder()
				.title("찍힌 라벨이 맞는지 확인해주세요")
				.description("수정 또는 인식이 안되었을 경우, 라벨 버튼을 꾹 눌러 수정해주세요")
				.build()
		);
	}
}
