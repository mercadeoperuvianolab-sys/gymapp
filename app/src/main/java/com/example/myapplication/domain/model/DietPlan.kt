package com.example.myapplication.domain.model

data class DietPlan(
    val userId: Long,
    val goal: GoalType,
    val caloriesTarget: Int,
    val proteinTarget: Int,
    val carbTarget: Int,
    val fatTarget: Int,
    val updatedAtEpochMillis: Long = System.currentTimeMillis()
)
