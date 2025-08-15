package com.schibsted.nde.data

import com.schibsted.nde.api.BackendApi
import com.schibsted.nde.data.common.ModelEntityMapper.mapToEntities
import com.schibsted.nde.data.common.ModelEntityMapper.mapToModel
import com.schibsted.nde.data.common.ModelEntityMapper.mapToModels
import com.schibsted.nde.data.common.ModelEntityMapper.mapToEntity
import com.schibsted.nde.data.common.NetworkBoundResource
import com.schibsted.nde.database.MealEntityDao
import com.schibsted.nde.model.MealResponse
import com.schibsted.nde.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsRepository @Inject constructor(
    private val backendApi: BackendApi,
    private val mealDao: MealEntityDao,
    private val networkBoundResource: NetworkBoundResource
) {

    fun getMeals(shouldRefresh: Boolean): Flow<Result<List<MealResponse>>> {
        return networkBoundResource(
            query = {
                mealDao.getAll().map { it.mapToModels() }
            },
            fetch = {
                backendApi.getMeals()
            },
            saveFetched = { remoteMeals ->
                mealDao.insertAll(remoteMeals.mapToEntities())
            },
            shouldFetch = { localMeals ->
                localMeals.isNullOrEmpty() || shouldRefresh
            }
        )
    }

    fun getMeal(id: String): Flow<Result<MealResponse>> {
        return networkBoundResource(
            query = {
                mealDao.getById(id).map { it.mapToModel() }
            },
            fetch = {
                backendApi.getMeal(id).meals.first()
            },
            saveFetched = { remoteMeal ->
                mealDao.insert(remoteMeal.mapToEntity())
            },
            shouldFetch = { localMeal ->
                localMeal == null
            }
        )
    }
}