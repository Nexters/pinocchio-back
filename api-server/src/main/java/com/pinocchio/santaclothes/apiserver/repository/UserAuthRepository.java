package com.pinocchio.santaclothes.apiserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pinocchio.santaclothes.apiserver.entity.UserAuth;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, String> {
	Optional<UserAuth> findTop1ByUserIdOrderByCreatedDateDesc(String userId);

	Optional<UserAuth> findTop1ByRefreshTokenOrderByCreatedDateDesc(String refreshToken);

	Optional<UserAuth> findByAccessTokenOrderByCreatedDateDesc(String accessToken);
}
