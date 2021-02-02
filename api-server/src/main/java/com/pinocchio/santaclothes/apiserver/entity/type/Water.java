package com.pinocchio.santaclothes.apiserver.entity.type;

import static com.pinocchio.santaclothes.apiserver.entity.type.Nation.*;

public enum Water {
	HAND_KR(0, KR,"손 세탁 가능, 중성 세제"),
	FORBIDDEN_KR(1,KR, "물세탁 불가"),
	WASHER_30_NEUTRAL_KR(2,KR, "세탁기 약 30도 중성"),
	WASHER_ABOUT_40_KR(3, KR,"세탁기 약 40도"),
	WASHER_40_KR(4, KR,"세탁기 40도"),
	WASHER_60_KR(5, KR,"세탁기 60도"),
	WASHER_90_KR(6, KR,"세탁기 95도"),
	HAND_USA(7,USA,"손 세탁 가능"),
	FORBIDDEN_USA(8, USA, "물 세탁 금지"),
	WASHER_USA(9, USA, "기계 세탁 가능"),
	FORBIDDEN_BLOWER_USA(10, USA, "비틀기 금지"),
	HAND_30_JP(11, JP, "30도 손세탁 가능"),
	WASHER_95_JP(12, JP, "세탁기 95도"),
	HAND_WEAK_JP(13, JP, "약한 손세탁"),
	FORBIDDEN_BLOWER_JP(14, JP, "비틀기 금지"),
	FORBIDDEN_JP(15,JP, "물세탁 불가"),
	;

	Water(int code, Nation nation, String description) {
		this.code = code;
		this.nation = nation;
		this.description = description;
	}

	int code;
	Nation nation;
	String description;

}
