package com.example.myapplication.domain.model

data class MealEntry(
    val id: Long = 0,
    val userId: Long,
    val name: String,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val consumedAtEpochMillis: Long = System.currentTimeMillis()
)
