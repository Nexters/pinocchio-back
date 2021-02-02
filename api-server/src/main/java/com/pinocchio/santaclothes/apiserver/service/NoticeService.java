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
				.title("라벨을 찍어보세요1")
				.description("라벨을 찍고 세탁법을 확인해보세요1")
				.content("라벨을 찍고 어떻게 확인하냐면 잘 해보세요1\n"
					+ "라벨을 찍고 어떻게 확인하냐면 잘 해보세요1\n"
					+ "라벨을 찍고 어떻게 확인하냐면 잘 해보세요1\n")
				.build(),

			Notice.builder()
				.title("라벨을 찍어보세요2")
				.description("라벨을 찍고 세탁법을 확인해보세요2")
				.content("라벨을 찍고 어떻게 확인하냐면 잘 해보세요2\n"
					+ "라벨을 찍고 어떻게 확인하냐면 잘 해보세요2\n"
					+ "라벨을 찍고 어떻게 확인하냐면 잘 해보세요2\n")
				.build(),

			Notice.builder()
				.title("라벨을 찍어보세요3")
				.description("라벨을 찍고 세탁법을 확인해보세요3")
				.content("라벨을 찍고 어떻게 확인하냐면 잘 해보세요3\n"
					+ "라벨을 찍고 어떻게 확인하냐면 잘 해보세요3\n"
					+ "라벨을 찍고 어떻게 확인하냐면 잘 해보세요3\n")
				.build()
		);
	}
}
