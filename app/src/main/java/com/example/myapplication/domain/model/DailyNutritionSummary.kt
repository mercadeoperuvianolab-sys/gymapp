package com.example.myapplication.domain.model

data class DailyNutritionSummary(
    val calorieConsumed: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fat: Int = 0,
    val limitCalories: Int = 0,
    val limitProtein: Int = 0,
    val limitCarbs: Int = 0,
    val limitFat: Int = 0,
    val deviationPercent: Double = 0.0,
    val isInAlertRange: Boolean = false
)
