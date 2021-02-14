package com.pinocchio.santaclothes.apiserver.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.entity.Cloth;
import com.pinocchio.santaclothes.apiserver.repository.ClothRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClothService {
	private final ClothRepository clothRepository;

	public void save(Cloth cloth) {
		clothRepository.save(cloth);
	}
}
