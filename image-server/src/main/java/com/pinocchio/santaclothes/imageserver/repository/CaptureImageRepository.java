package com.pinocchio.santaclothes.imageserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pinocchio.santaclothes.imageserver.entity.CaptureImage;

@Repository
public interface CaptureImageRepository extends JpaRepository<CaptureImage, String> {
}
