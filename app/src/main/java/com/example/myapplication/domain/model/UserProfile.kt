package com.example.myapplication.domain.model

data class UserProfile(
    val id: Long = 0,
    val name: String,
    val goal: GoalType,
    val caloriesTarget: Int,
    val proteinTarget: Int,
    val carbTarget: Int,
    val fatTarget: Int
)
