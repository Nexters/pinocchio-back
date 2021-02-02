package com.pinocchio.santaclothes.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pinocchio.santaclothes.apiserver.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
