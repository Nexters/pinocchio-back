package com.pinocchio.santaclothes.apiserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pinocchio.santaclothes.apiserver.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findBySocialId(String socialId);
}
