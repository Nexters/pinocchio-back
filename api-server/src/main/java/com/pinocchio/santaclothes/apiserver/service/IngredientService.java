package com.pinocchio.santaclothes.apiserver.service;

import org.springframework.stereotype.Service;

import com.pinocchio.santaclothes.apiserver.domain.ingredient.IngredientAnalyzeResult;
import com.pinocchio.santaclothes.apiserver.domain.ingredient.IngredientAnalyzer;
import com.pinocchio.santaclothes.apiserver.domain.ingredient.IngredientSource;

@Service
public class IngredientService {
	private final IngredientAnalyzer ingredientAnalyzer = new IngredientAnalyzer();

	public IngredientAnalyzeResult analyze(IngredientSource ingredientSource) {
		return ingredientAnalyzer.analyze(ingredientSource);
	}
}
