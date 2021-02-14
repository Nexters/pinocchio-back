package com.pinocchio.santaclothes.apiserver.service;

import org.springframework.stereotype.Service;

@Service
public class GlobalCountService {
	// TODO: redis 같은 걸로 count 할 예정
	private long globalCount = 0;

	public long getCount() {
		return globalCount;
	}

	public void incrementCount() {
		globalCount++;
	}
}
