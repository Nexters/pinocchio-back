package com.pinocchio.santaclothes.apiserver.entity.type;

import static com.pinocchio.santaclothes.apiserver.entity.type.Nation.*;

public enum DryCleaning {
	DRY_CLEANING_KR(0, KR, "드라이 클리닝 가능"),
	DRY_CLEANING_FORBIDDEN_KR(1, KR, "드라이 클리닝 불가"),
	DRY_CLEANING_OIL_KR(2, KR, "석유계 드라이 클리닝 가능"),
	DRY_CLEANING_SELF_FORBIDDEN_KR(3, KR, "셀프 드라이 클리닝 불가"),
	DRY_CLEANING_USA(4, USA, "드라이 클리닝 가능"),
	DRY_CLEANING_FORBIDDEN_USA(5, USA, "드라이 클리닝 불가"),
	DRY_CLEANING_SHORT_USA(6, USA, "짧은 드라이 클리닝"),
	DRY_CLEANING_LOW_HUMIDITY_USA(7, USA, "낮은 습도에서 드라이 클리닝"),
	DRY_CLEANING_NOT_HOT_USA(8, USA, "열을 줄여서 드라이 클리닝"),
	DRY_CLEANING_JP(9, JP, "드라이 클리닝 가능"),
	DRY_CLEANING_FORBIDDEN_JP(10, JP, "드라이 클리닝 불가"),
	;
	int code;
	Nation nation;
	String description;

	DryCleaning(int code, Nation nation, String description) {
		this.code = code;
		this.nation = nation;
		this.description = description;
	}
}
