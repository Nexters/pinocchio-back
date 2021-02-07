package com.pinocchio.santaclothes.imageserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pinocchio.santaclothes.common.type.ClothesType;
import com.pinocchio.santaclothes.imageserver.entity.CaptureImage;

@Repository
public interface CaptureImageRepository extends JpaRepository<CaptureImage, String> {

	List<CaptureImage> findByUserId(String userId);
}
