package com.example.myapplication.ui.screens.dashboard

import com.example.myapplication.domain.model.GoalType

data class DashboardUiState(
    val userName: String = "Usuario",
    val goal: GoalType = GoalType.MAINTAIN,
    val consumedCalories: Int = 0,
    val targetCalories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fat: Int = 0,
    val alert: Boolean = false
)
