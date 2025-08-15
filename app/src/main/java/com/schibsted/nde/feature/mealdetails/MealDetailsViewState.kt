package com.schibsted.nde.feature.mealdetails

import com.schibsted.nde.model.MealResponse

data class MealDetailsViewState(
    val meal: MealResponse? = null,
    val errorMessage: String? = null
)
