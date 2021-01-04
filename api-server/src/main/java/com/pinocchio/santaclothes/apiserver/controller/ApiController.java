package com.pinocchio.santaclothes.apiserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	@GetMapping("/healthCheck")
	public String healthCheck() {
		return "ok";
	}
}
