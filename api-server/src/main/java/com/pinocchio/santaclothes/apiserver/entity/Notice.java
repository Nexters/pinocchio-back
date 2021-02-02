package com.pinocchio.santaclothes.apiserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String title;

	String description;

	String content;
}
