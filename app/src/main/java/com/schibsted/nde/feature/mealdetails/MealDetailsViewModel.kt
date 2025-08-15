package com.schibsted.nde.feature.mealdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schibsted.nde.data.MealsRepository
import com.schibsted.nde.model.MealResponse
import com.schibsted.nde.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    mealsRepository: MealsRepository
): ViewModel() {

    private val mealId = savedStateHandle.get<String>("mealId").orEmpty()

    val state = mealsRepository.getMeal(mealId)
        .map { it.mapToViewState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1_000),
            initialValue = MealDetailsViewState()
        )

    private fun Result<MealResponse>.mapToViewState() =
        when (this) {
            is Result.Success -> MealDetailsViewState(data)
            else -> MealDetailsViewState()
        }
}