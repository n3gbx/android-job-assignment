package com.schibsted.nde.feature.mealdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.schibsted.nde.feature.common.MealImage

@Composable
fun MealDetailsScreen(
    viewModel: MealDetailsViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Meal details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "close"
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MealImage(
                modifier = Modifier.size(256.dp),
                thumb = state.meal?.strMealThumb.orEmpty(),
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.meal?.strMeal.orEmpty(),
                style = MaterialTheme.typography.h5
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.meal?.strInstructions.orEmpty(),
                style = MaterialTheme.typography.body1
            )
        }
    }
}