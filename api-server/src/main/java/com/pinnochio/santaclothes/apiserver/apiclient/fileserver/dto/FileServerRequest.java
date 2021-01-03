package com.pinnochio.santaclothes.apiserver.apiclient.fileserver.dto;

import org.springframework.core.io.Resource;

import lombok.Value;

@Value
public class FileServerRequest {
	Long userId;

	Resource file;
}
