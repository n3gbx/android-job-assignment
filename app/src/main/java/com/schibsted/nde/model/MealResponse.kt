package com.schibsted.nde.model

data class MealResponse(
    val idMeal: String,
    val strMeal: String,
    val strInstructions: String,
    val strCategory: String,
    val strMealThumb: String,
    val strYoutube: String?,
)