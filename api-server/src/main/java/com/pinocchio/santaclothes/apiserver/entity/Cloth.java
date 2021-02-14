package com.pinocchio.santaclothes.apiserver.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.pinocchio.santaclothes.apiserver.entity.type.BleachType;
import com.pinocchio.santaclothes.apiserver.entity.type.ClothesColor;
import com.pinocchio.santaclothes.apiserver.entity.type.DryCleaning;
import com.pinocchio.santaclothes.apiserver.entity.type.DryType;
import com.pinocchio.santaclothes.apiserver.entity.type.IroningType;
import com.pinocchio.santaclothes.apiserver.entity.type.WaterType;

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
public class Cloth {
	@Id
	private String clothId;

	private String eventId;

	private String userId;

	private String imageId;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cloth", cascade = CascadeType.ALL)
	List<Ingredient> ingredientList;

	@Enumerated(EnumType.STRING)
	@NotNull
	private WaterType waterType;

	@Enumerated(EnumType.STRING)
	@NotNull
	private BleachType bleachType;

	@Enumerated(EnumType.STRING)
	@NotNull
	private IroningType ironingType;

	@Enumerated(EnumType.STRING)
	@NotNull
	private DryType dryType;

	@Enumerated(EnumType.STRING)
	@NotNull
	private DryCleaning dryCleaning;

	@Enumerated(EnumType.STRING)
	@NotNull
	private ClothesColor clothesColor;
}
