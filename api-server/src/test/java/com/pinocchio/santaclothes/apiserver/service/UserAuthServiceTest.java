package com.pinocchio.santaclothes.apiserver.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pinocchio.santaclothes.apiserver.domain.UserAuth;
import com.pinocchio.santaclothes.apiserver.exception.TokenInvalidException;
import com.pinocchio.santaclothes.apiserver.repository.UserAuthRepository;
import com.pinocchio.santaclothes.apiserver.test.SpringTest;

class UserAuthServiceTest extends SpringTest {
	@Autowired
	private UserService sut;

	@MockBean
	private UserAuthRepository userAuthRepository;

	@Test
	void refreshTokenExpired() {
		String userId = "userId";
		String refreshToken = "refreshToken";
		Instant expiredCreatedDate = Instant.now().minus(50, ChronoUnit.DAYS);
		String accessToken = "accessToken";
		UserAuth mockUserAuth = UserAuth.builder()
			.id(1L)
			.userId(userId)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.createdDate(expiredCreatedDate)
			.build();

		given(userAuthRepository.findTop1ByRefreshTokenOrderByCreatedDateDesc(refreshToken))
			.willReturn(Optional.of(mockUserAuth));

		assertThatThrownBy(() -> sut.refresh(refreshToken))
			.isInstanceOf(TokenInvalidException.class);

	}
}
