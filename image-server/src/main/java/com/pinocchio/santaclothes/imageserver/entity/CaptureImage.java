package com.pinocchio.santaclothes.imageserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

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

	String originalFileName;

	String savedFileName;

	String filePath;
}
