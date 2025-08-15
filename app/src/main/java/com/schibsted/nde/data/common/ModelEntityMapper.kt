package com.schibsted.nde.data.common

import com.schibsted.nde.database.MealEntity
import com.schibsted.nde.model.MealResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelEntityMapper @Inject constructor() {

    fun MealEntity.mapToModel() =
        MealResponse(
            idMeal = id,
            strMeal = title,
            strInstructions = instructions,
            strCategory = category,
            strMealThumb = imageUrl,
            strYoutube = youtubeUrl,
        )

    fun MealResponse.mapToEntity() =
        MealEntity(
            id = idMeal,
            title = strMeal,
            category = strCategory,
            instructions = strInstructions,
            imageUrl = strMealThumb,
            youtubeUrl = strYoutube,
        )
}