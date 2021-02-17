package com.pinocchio.santaclothes.apiserver.entity.type;

import static com.pinocchio.santaclothes.apiserver.entity.type.NationType.*;

public enum BleachType {
	FORBIDDEN_KR(0, KR, "염소, 산소, 표백 불가"),
	CL_KR(1, KR, "산소, 표백 불가"),
	O2_KR(2, KR, "염소, 표백 불가"),
	ALL_KR(3, KR, "염소, 산소, 표백 가능"),
	O2_BLEACH_KR(4,KR, "산소, 표백 가능"),
	CL_BLEACH_KR(5,KR,"염소 표백 가능"),
	FORBIDDEN_USA(6, USA, "염소, 산소, 표백 불가"),
	O2_COLOR_USA(7, USA, "비염소제와 색상보호 표백제만 가능"),
	ALL_USA(8, USA, "염소, 산소, 표백 가능"),
	FORBIDDEN_JP(9, JP, "염소, 산소, 표백 불가"),
	ALL_JP(10, JP, "염소, 산소, 표백 가능"),
	;

	BleachType(int code, NationType nationType, String description) {
		this.code = code;
		this.nationType = nationType;
		this.description = description;
	}

	int code;
	NationType nationType;
	String description;
}
