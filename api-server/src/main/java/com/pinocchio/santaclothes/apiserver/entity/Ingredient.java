package com.pinocchio.santaclothes.apiserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ingredientId;

	private String name;

	private int percentage;

	public Ingredient(String name, int percentage) {
		this.name = name;
		this.percentage = percentage;
	}

	@ManyToOne // FK
	@JoinColumn(name = "cloth_id", nullable = false)
	private Cloth cloth;
}
