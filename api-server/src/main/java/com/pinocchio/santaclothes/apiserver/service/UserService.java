package com.pinocchio.santaclothes.apiserver.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.domain.User;
import com.pinocchio.santaclothes.apiserver.domain.UserAuth;
import com.pinocchio.santaclothes.apiserver.exception.DuplicateUserException;
import com.pinocchio.santaclothes.apiserver.repository.UserAuthRepository;
import com.pinocchio.santaclothes.apiserver.repository.UserRepository;
import com.pinocchio.santaclothes.common.utils.Uuids;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private final UserAuthRepository userAuthRepository;

	public void register(String socialId, String nickName) {
		if (userRepository.findBySocialId(socialId).isPresent()) {
			throw new DuplicateUserException();
		}

		String userId = Uuids.generateUuidString();
		User user = User.builder()
			.id(userId)
			.socialId(socialId)
			.nickName(nickName)
			.build();
		userRepository.save(user);
	}

	public void login(String userId) {

	}

	public void refresh(String refreshToken) {
		UserAuth userAuth = userAuthRepository.findTop1ByRefreshTokenOrderByCreatedDateDesc(refreshToken).orElseThrow();

	}
}
