package com.pinocchio.santaclothes.apiserver.assembler;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.assembler.domain.MainView;
import com.pinocchio.santaclothes.apiserver.assembler.domain.MyPageView;
import com.pinocchio.santaclothes.apiserver.assembler.domain.NoticeView;
import com.pinocchio.santaclothes.apiserver.assembler.domain.ResultView;
import com.pinocchio.santaclothes.apiserver.entity.User;
import com.pinocchio.santaclothes.apiserver.entity.type.BleachType;
import com.pinocchio.santaclothes.apiserver.entity.type.DryCleaning;
import com.pinocchio.santaclothes.apiserver.entity.type.DryType;
import com.pinocchio.santaclothes.apiserver.entity.type.IroningType;
import com.pinocchio.santaclothes.apiserver.entity.type.WaterType;
import com.pinocchio.santaclothes.apiserver.exception.EventInvalidException;
import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.service.CaptureService;
import com.pinocchio.santaclothes.apiserver.service.GlobalCountService;
import com.pinocchio.santaclothes.apiserver.service.NoticeService;
import com.pinocchio.santaclothes.apiserver.service.UserService;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventDto;
import com.pinocchio.santaclothes.apiserver.service.dto.CaptureEventResultDto;
import com.pinocchio.santaclothes.common.type.ClothesType;
import com.pinocchio.santaclothes.common.type.IngredientType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewAssembler {
	private final CaptureService captureService;
	private final UserService userService;
	private final GlobalCountService globalCountService;
	private final NoticeService noticeService;


	public MainView assembleMain(String accessToken) {
		User user = userService.findByAccessToken(accessToken);

		long count = globalCountService.getCount();
		List<NoticeView> noticeViewList = noticeService.findAllNotice().stream()
			.map(
				it -> NoticeView.builder()
					.title(it.getTitle())
					.description(it.getDescription())
					.content(it.getContent())
					.build()
			)
			.collect(toList());
		String nickName = user.getNickName();

		return MainView.builder()
			.nickName(nickName)
			.globalCount(count)
			.notices(noticeViewList)
			.build();
	}

	public ResultView resultView(String eventId) {
		CaptureEventDto captureEventDto = captureService.findById(eventId);
		CaptureEventResultDto resultDto = captureEventDto.getResult();

		if (resultDto == null) {
			throw new EventInvalidException(eventId, ExceptionReason.EVENT_NO_RESULT);
		}

		return ResultView.builder()
			.waterType(resultDto.getWaterType())
			.ironingType(resultDto.getIroningType())
			.dryCleaning(resultDto.getDryCleaning())
			.dryType(resultDto.getDryType())
			.bleachType(resultDto.getBleachType())
			.ingredients(resultDto.getIngredientList())
			.build();
	}

	public MyPageView myPageView(String authorization) {
		// TODO: myPageView 조립해서 만들기
		List<NoticeView> noticeViewList = List.of(
			NoticeView.builder()
				.title("산타클로즈에 라벨을 쌓아주세요")
				.content("라벨 10개를 찍으면 상품이 꽝")
				.build()
		);

		Map<ClothesType, List<ResultView>> clothesTypeListMap = Map.of(
			ClothesType.TOP, List.of(
				ResultView.builder()
					.bleachType(BleachType.ALL_JP)
					.dryType(DryType.DRY_CLOUD_DOWN_JP)
					.dryCleaning(DryCleaning.DRY_CLEANING_FORBIDDEN_JP)
					.ironingType(IroningType.IRONING_80_120_JP)
					.waterType(WaterType.FORBIDDEN_BLOWER_JP)
					.ingredients(
						List.of(
							new IngredientType("폴리에스테르", 100)
						)
					)
					.build()
			)
		);

		return MyPageView.builder()
			.noticeViewList(noticeViewList)
			.clothesByClothesType(clothesTypeListMap)
			.myLabelCount(0L)
			.build();
	}
}
