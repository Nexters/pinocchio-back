package com.pinocchio.santaclothes.apiserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {
	@GetMapping("/healthCheck")
	public String healthCheck() {
		return "ok";
	}
}


