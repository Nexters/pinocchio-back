package com.pinocchio.santaclothes.apiserver.service;

import org.springframework.stereotype.Service;

@Service
public class GlobalCountService {
	private long globalCount = 0;

	public long getCount() {
		return globalCount;
	}

	public void incrementCount() {
		globalCount++;
	}
}
