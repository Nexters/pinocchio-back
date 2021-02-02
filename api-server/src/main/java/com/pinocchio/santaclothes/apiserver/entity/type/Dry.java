package com.pinocchio.santaclothes.apiserver.entity.type;

import static com.pinocchio.santaclothes.apiserver.entity.type.Nation.*;

public enum Dry {
	DRY_SUNNY_HANGER_KR(0, KR, "화창한 날 옷걸이에 걸어서"),
	DRY_CLOUD_HANGER_KR(1, KR, "흐린 날 옷걸이에 걸어서"),
	DRY_SUNNY_DOWN_KR(2, KR, "화창한 날 눕혀서"),
	DRY_CLOUD_DOWN_KR(3, KR, "흐린 날 눕혀서"),
	DRY_BLOWER_KR(4, KR, "비틀기 가능"),
	DRY_BLOWER_FORBIDDEN_KR(5, KR, "비틀기 불가"),
	DRY_MACHINE_KR(6, KR, "건조기 가능"),
	DRY_MACHINE_FORBIDDEN_KR(7, KR, "건조기 불가"),
	DRY_MACHINE_USA(8, USA, "건조기 가능"),
	DRY_MACHINE_FORBIDDEN_USA(9, USA, "건조기 불가"),
	DRY_WET_HANGER_USA(10, USA, "젖은 채로 줄이나 막대기에 널어서 건조"),
	DRY_HANGER_USA(11, USA, "줄에 널어서 건조"),
	DRY_DOWN_USA(12, USA, "평평하게 펴서 건조"),
	DRY_SHADE_USA(13, USA, "그늘에서 건조"),
	DRY_HANGER_JP(14, JP, "옷걸이에 걸어서 건조"),
	DRY_CLOUD_HANGER_JP(15, JP, "흐른 날에 옷걸이에 걸어서 건조"),
	DRY_DOWN_JP(16, JP, "눕혀서 건조"),
	DRY_CLOUD_DOWN_JP(17, JP, "흐린 날에 눕혀서 건조"),
	;

	int code;
	Nation nation;
	String description;

	Dry(int code, Nation nation, String description) {
		this.code = code;
		this.nation = nation;
		this.description = description;
	}
}
