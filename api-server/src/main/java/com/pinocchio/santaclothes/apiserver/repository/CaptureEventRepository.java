package com.pinocchio.santaclothes.apiserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pinocchio.santaclothes.apiserver.entity.CaptureEvent;
import com.pinocchio.santaclothes.apiserver.type.CaptureEventStatus;

public interface CaptureEventRepository extends JpaRepository<CaptureEvent, String> {
	List<CaptureEvent> findByStatus(CaptureEventStatus status);
}
