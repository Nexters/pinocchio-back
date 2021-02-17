package com.pinocchio.santaclothes.apiserver.entity.type;

import static com.pinocchio.santaclothes.apiserver.entity.type.NationType.*;

public enum IroningType {
	FORBIDDEN_KR(0, KR, "다림질 불가"),
	IRONING_80_120_KR(1, KR, "80~120도 가능"),
	IRONING_80_120_FABRIC_KR(2, KR, "80~120도 천 깔고만 가능"),
	IRONING_140_160_KR(3, KR, "140~160도 가능"),
	IRONING_140_160_FABRIC_KR(4, KR, "140~160도 천 깔고만 가능"),
	IRONING_180_210_KR(5, KR, "180~210도 가능"),
	IRONING_180_210_FABRIC_KR(6, KR, "180~210도 천 깔고만 가능"),
	FORBIDDEN_USA(7, USA, "다림질 불가"),
	FORBIDDEN_STEAM_USA(8, USA, "스팀 다리미 불가"),
	STEAM_USA(9, USA, "스팀 다리미 가능"),
	FORBIDDEN_JP(10, JP, "다림질 불가"),
	IRONING_80_120_JP(11, JP, "80~120도"),
	IRONING_140_160_JP(12, JP, "140~160도"),
	IRONING_180_210_JP(13, JP, "180~210도"),
	;

	int code;
	NationType nationType;
	String description;

	IroningType(int code, NationType nationType, String description) {
		this.code = code;
		this.nationType = nationType;
		this.description = description;
	}
}
