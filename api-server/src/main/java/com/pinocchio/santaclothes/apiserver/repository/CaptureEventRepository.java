package com.pinocchio.santaclothes.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pinocchio.santaclothes.apiserver.entity.CaptureEvent;

public interface CaptureEventRepository extends JpaRepository<CaptureEvent, String> {
}
