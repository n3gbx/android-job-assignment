package com.schibsted.nde.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class MealEntity(
    @PrimaryKey val id: String,
    val title: String,
    val instructions: String,
    val category: String,
    val imageUrl: String,
    val youtubeUrl: String?,
)
