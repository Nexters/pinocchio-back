package com.pinocchio.santaclothes.apiserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pinocchio.santaclothes.apiserver.assembler.ViewAssembler;
import com.pinocchio.santaclothes.apiserver.assembler.domain.MainView;
import com.pinocchio.santaclothes.apiserver.assembler.domain.MyPageView;
import com.pinocchio.santaclothes.apiserver.assembler.domain.ResultView;
import com.pinocchio.santaclothes.apiserver.exception.ExceptionReason;
import com.pinocchio.santaclothes.apiserver.exception.ProblemModel;
import com.pinocchio.santaclothes.apiserver.exception.TokenInvalidException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(tags = "View")
@RestController
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {
	private final ViewAssembler viewAssembler;

	@ApiOperation("메인 뷰 조회")
	@GetMapping("/main")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "뷰 조회 성공"),
		@ApiResponse(code = 401, message = "인증 에러", response = ProblemModel.class),
		@ApiResponse(code = 500, message = "서버 에러", response = ProblemModel.class),
	})
	@ResponseStatus(HttpStatus.OK)
	public MainView mainView(@ApiParam(hidden = true) @RequestHeader("authorization") String authorization) {
		return viewAssembler.assembleMain(getToken(authorization));
	}

	@ApiOperation("마이 페이지 뷰 조회")
	@GetMapping("/myPage")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "뷰 조회 성공"),
		@ApiResponse(code = 401, message = "인증 에러", response = ProblemModel.class),
		@ApiResponse(code = 500, message = "서버 에러", response = ProblemModel.class),
	})
	@ResponseStatus(HttpStatus.OK)
	public MyPageView myPageView(@ApiParam(hidden = true) @RequestHeader("authorization") String authorization) {
		return viewAssembler.myPageView(getToken(authorization));
	}

	@ApiOperation("결과 뷰 조회")
	@GetMapping("/result/{eventId}")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "뷰 조회 성공"),
		@ApiResponse(code = 401, message = "인증 에러", response = ProblemModel.class),
		@ApiResponse(code = 500, message = "서버 에러", response = ProblemModel.class),
	})
	@ResponseStatus(HttpStatus.OK)
	public ResultView resultView(@PathVariable String eventId) {
		return viewAssembler.resultView(eventId);
	}

	private String getToken(String authorization) {
		if (authorization.isBlank() || authorization.length() < 8) {
			throw new TokenInvalidException(ExceptionReason.INVALID_ACCESS_TOKEN);
		}
		return authorization.substring(7);
	}
}
