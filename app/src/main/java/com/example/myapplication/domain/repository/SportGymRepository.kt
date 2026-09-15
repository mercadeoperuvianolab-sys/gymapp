package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.DailyNutritionSummary
import com.example.myapplication.domain.model.DietPlan
import com.example.myapplication.domain.model.MealEntry
import com.example.myapplication.domain.model.UserProfile
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface SportGymRepository {
    suspend fun getCurrentUserProfile(): UserProfile?

    suspend fun getLatestDietPlan(userId: Long): DietPlan?

    suspend fun upsertDietPlan(plan: DietPlan): Long

    suspend fun addMeal(meal: MealEntry): Long

    fun observeDailyNutritionSummary(userId: Long, day: LocalDate): Flow<DailyNutritionSummary>
}
