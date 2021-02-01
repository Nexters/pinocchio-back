package com.pinocchio.santaclothes.apiserver.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.domain.User;
import com.pinocchio.santaclothes.apiserver.domain.UserAuth;
import com.pinocchio.santaclothes.apiserver.exception.DatabaseException;
import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.exception.TokenInvalidException;
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
			throw new DatabaseException(ExceptionReason.DUPLICATE_ENTITY);
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
		User user = userRepository.findBySocialId(socialId)
			.orElseThrow(() -> new TokenInvalidException(ExceptionReason.SOCIAL_KEY_NOT_EXISTS));
		String userId = user.getId();
		Instant now = Instant.now();

		String authToken = Uuids.generateUuidString();
		String refreshToken = Uuids.generateUuidString();

		UserAuth newAuth = UserAuth.builder()
			.userId(userId)
			.accessToken(authToken)
			.refreshToken(refreshToken)
			.expireDateTime(now.plus(30, ChronoUnit.DAYS))
			.build();
		userAuthRepository.save(newAuth);

		return newAuth;
	}

	public UserAuth refresh(String refreshToken) {
		Instant now = Instant.now();
		UserAuth userAuth = userAuthRepository.findTop1ByRefreshTokenOrderByCreatedDateDesc(refreshToken)
			.filter(it -> !it.isExpiredWhen(now))
			.orElseThrow(() -> new TokenInvalidException(ExceptionReason.INVALID_REFRESH_TOKEN));

		Instant expireDate = now.plus(30, ChronoUnit.DAYS);
		String newAuthToken = Uuids.generateUuidString();
		UserAuth newUserAuth = UserAuth.builder()
			.refreshToken(refreshToken)
			.accessToken(newAuthToken)
			.userId(userAuth.getUserId())
			.createdDate(now)
			.expireDateTime(expireDate)
			.build();
		userAuthRepository.save(newUserAuth);
		return newUserAuth;
	}

	public boolean isActiveToken(String accessToken) {
		String latestAccessToken = userAuthRepository.findByAccessTokenOrderByCreatedDateDesc(accessToken)
			.flatMap(it -> userAuthRepository.findTop1ByUserIdOrderByCreatedDateDesc(it.getUserId()))
			.map(UserAuth::getAccessToken)
			.orElseThrow(() -> new TokenInvalidException(ExceptionReason.INVALID_ACCESS_TOKEN));

		return accessToken.equals(latestAccessToken);
	}

	public boolean isExpired(String accessToken) {
		Instant now = Instant.now();
		return userAuthRepository.findByAccessTokenOrderByCreatedDateDesc(accessToken)
			.orElseThrow(() -> new TokenInvalidException(ExceptionReason.INVALID_ACCESS_TOKEN))
			.isExpiredWhen(now);
	}
}
