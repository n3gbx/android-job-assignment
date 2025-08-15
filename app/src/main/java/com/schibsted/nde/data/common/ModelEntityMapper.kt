package com.schibsted.nde.data.common

import com.schibsted.nde.database.MealEntity
import com.schibsted.nde.model.MealResponse
import com.schibsted.nde.model.MealsResponse

object ModelEntityMapper {

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


    fun MealsResponse.mapToEntities() = meals.map { it.mapToEntity() }

    fun List<MealEntity>.mapToModels() = map { it.mapToModel() }
}