package com.pinocchio.santaclothes.imageserver.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.pinocchio.santaclothes.common.type.ClothesType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@Entity
public class CaptureImage {
	@Id
	String imageId;

	String eventId;

	String userId;

	String originalFileName;

	String savedFileName;

	@Enumerated(EnumType.STRING)
	ClothesType clothesCategory;

	String filePath;

	long fileLength;
}
