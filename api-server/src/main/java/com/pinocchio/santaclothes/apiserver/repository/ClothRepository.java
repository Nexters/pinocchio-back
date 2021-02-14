package com.pinocchio.santaclothes.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pinocchio.santaclothes.apiserver.entity.Cloth;

public interface ClothRepository extends JpaRepository<Cloth, String> {
}
