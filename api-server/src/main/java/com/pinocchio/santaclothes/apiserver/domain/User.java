package com.pinocchio.santaclothes.apiserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class User {
	@Id
	private String id;

	@Column(unique = true)
	private String socialId;

	private String nickName;

	public User() {
	}
}
