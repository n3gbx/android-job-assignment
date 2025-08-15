package com.schibsted.nde.feature.meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schibsted.nde.data.MealsRepository
import com.schibsted.nde.model.MealResponse
import com.schibsted.nde.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
) : ViewModel() {

    private val refreshTriggerFlow = MutableSharedFlow<Boolean>()
    private val queryStateFlow = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = combine(
        queryStateFlow,
        refreshTriggerFlow
            .onStart { emit(false) }
            .flatMapLatest { getMeals(it) },
    ) { query, meals ->
        MealsViewState(
            filteredMeals = meals.filterByQuery(query),
            meals = meals,
            query = query,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1_000),
        initialValue = MealsViewState(isLoading = true)
    )

    fun onRefresh() {
        viewModelScope.launch {
            refreshTriggerFlow.emit(true)
        }
    }

    fun onQuery(query: String?) {
        queryStateFlow.value = query
    }

    private fun getMeals(shouldRefresh: Boolean) =
        mealsRepository
            .getMeals(shouldRefresh)
            .map { result ->
                (result as? Result.Success<List<MealResponse>>)?.data.orEmpty()
            }

    private fun List<MealResponse>.filterByQuery(searchQuery: String?) =
        if (!searchQuery.isNullOrBlank()) {
            filter { it.strMeal.lowercase().contains(searchQuery.lowercase()) }
        } else this
}