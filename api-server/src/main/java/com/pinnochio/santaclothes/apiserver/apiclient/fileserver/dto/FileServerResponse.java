package com.pinnochio.santaclothes.apiserver.apiclient.fileserver.dto;

import static com.pinnochio.santaclothes.apiserver.apiclient.fileserver.type.FileServerStatus.*;

import com.pinnochio.santaclothes.apiserver.apiclient.fileserver.type.FileServerStatus;

import lombok.Value;

@Value
public class FileServerResponse {
	FileServerStatus status;

	String message;

	public boolean isSuccess(){
		return SUCCEED.equals(status);
	}
}
