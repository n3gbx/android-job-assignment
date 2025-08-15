package com.schibsted.nde.api

import com.schibsted.nde.model.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BackendApi {
    @GET("/api/json/v1/1/search.php?s=chicken")
    suspend fun getMeals(): MealsResponse

    @GET("/api/json/v1/1/lookup.php")
    suspend fun getMeal(@Query("i") id: String): MealsResponse
}