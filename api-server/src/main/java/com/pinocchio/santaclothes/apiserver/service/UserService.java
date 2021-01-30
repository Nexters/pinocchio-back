package com.pinocchio.santaclothes.apiserver.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.domain.User;
import com.pinocchio.santaclothes.apiserver.domain.UserAuth;
import com.pinocchio.santaclothes.apiserver.exception.DuplicateUserException;
import com.pinocchio.santaclothes.apiserver.exception.TokenExpiredException;
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

	public UserAuth login(String socialId) {
		User user = userRepository.findBySocialId(socialId).orElseThrow();
		String userId = user.getId();
		Instant now = Instant.now();

		String authToken = Uuids.generateUuidString();
		String refreshToken = Uuids.generateUuidString();

		Optional<UserAuth> optionalAuth = userAuthRepository.findTop1ByUserIdOrderByCreatedDateDesc(userId);

		if (optionalAuth.isPresent()) {
			UserAuth auth = optionalAuth.get();
			if (auth.isExpiredWhen(now)) {
				throw new TokenExpiredException();
			}
			return auth;
		}

		UserAuth newAuth = UserAuth.builder()
			.userId(userId)
			.authToken(authToken)
			.refreshToken(refreshToken)
			.expireDate(now.plus(30, ChronoUnit.DAYS))
			.build();
		userAuthRepository.save(newAuth);
		return newAuth;
	}

	public UserAuth refresh(String refreshToken) {
		UserAuth userAuth = userAuthRepository.findTop1ByRefreshTokenOrderByCreatedDateDesc(refreshToken).orElseThrow();
		Instant now = Instant.now();
		Instant expireDate = now.plus(30, ChronoUnit.DAYS);
		String newAuthToken = userAuth.getAuthToken();
		if (!userAuth.isExpiredWhen(now)) {
			UserAuth newUserAuth = UserAuth.builder()
				.refreshToken(refreshToken)
				.authToken(newAuthToken)
				.userId(userAuth.getUserId())
				.createdDate(now)
				.expireDate(expireDate)
				.build();
			userAuthRepository.save(newUserAuth);
			return newUserAuth;
		}
		return userAuth;

	}
}
