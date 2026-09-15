package com.example.myapplication.data.repository

import com.example.myapplication.data.local.SportGymDatabase
import com.example.myapplication.data.local.entities.DietPlanEntity
import com.example.myapplication.data.local.entities.MealEntity
import com.example.myapplication.data.local.entities.UserEntity
import com.example.myapplication.domain.model.DailyNutritionSummary
import com.example.myapplication.domain.model.DietPlan
import com.example.myapplication.domain.model.GoalType
import com.example.myapplication.domain.model.MealEntry
import com.example.myapplication.domain.model.UserProfile
import com.example.myapplication.domain.repository.SportGymRepository
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.abs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SportGymRepositoryImpl(
    private val database: SportGymDatabase
) : SportGymRepository {

    override suspend fun getCurrentUserProfile(): UserProfile? {
        val userEntity = database.userDao().getCurrentUser() ?: return null
        val latestPlan = database.dietPlanDao().getLatestPlanForUser(userEntity.id)
        val goal = parseGoal(userEntity.goal)
        val caloriesTarget = latestPlan?.caloriesTarget ?: userEntity.caloriesTarget
        val proteinTarget = latestPlan?.proteinTarget ?: userEntity.proteinTarget
        val carbTarget = latestPlan?.carbTarget ?: userEntity.carbTarget
        val fatTarget = latestPlan?.fatTarget ?: userEntity.fatTarget

        return UserProfile(
            id = userEntity.id,
            name = userEntity.name,
            goal = goal,
            caloriesTarget = caloriesTarget,
            proteinTarget = proteinTarget,
            carbTarget = carbTarget,
            fatTarget = fatTarget
        )
    }

    override suspend fun getLatestDietPlan(userId: Long): DietPlan? {
        val entity = database.dietPlanDao().getLatestPlanForUser(userId) ?: return null
        return entity.toDomain()
    }

    override suspend fun upsertDietPlan(plan: DietPlan): Long {
        val entity = DietPlanEntity(
            userId = plan.userId,
            goal = plan.goal.name,
            caloriesTarget = plan.caloriesTarget,
            proteinTarget = plan.proteinTarget,
            carbTarget = plan.carbTarget,
            fatTarget = plan.fatTarget,
            updatedAtMillis = plan.updatedAtEpochMillis
        )
        return database.dietPlanDao().upsertDietPlan(entity)
    }

    override suspend fun addMeal(meal: MealEntry): Long {
        val entity = MealEntity(
            id = meal.id,
            userId = meal.userId,
            name = meal.name,
            calories = meal.calories,
            protein = meal.protein,
            carbs = meal.carbs,
            fat = meal.fat,
            consumedAtEpochMillis = meal.consumedAtEpochMillis
        )
        return database.mealDao().insertMeal(entity)
    }

    override fun observeDailyNutritionSummary(userId: Long, day: LocalDate): Flow<DailyNutritionSummary> {
        val startMillis = day.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endMillis = day.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        return database.mealDao()
            .observeMealsByDay(userId, startMillis, endMillis)
            .map { meals ->
                val latestPlan = database.dietPlanDao().getLatestPlanForUser(userId)
                val consumedCalories = meals.sumOf { it.calories }
                val consumedProtein = meals.sumOf { it.protein }
                val consumedCarbs = meals.sumOf { it.carbs }
                val consumedFat = meals.sumOf { it.fat }
                val limitCalories = latestPlan?.caloriesTarget ?: 0
                val limitProtein = latestPlan?.proteinTarget ?: 0
                val limitCarbs = latestPlan?.carbTarget ?: 0
                val limitFat = latestPlan?.fatTarget ?: 0
                val deviation = if (limitCalories > 0) {
                    ((consumedCalories.toDouble() - limitCalories.toDouble()) / limitCalories.toDouble()) * 100.0
                } else {
                    0.0
                }

                DailyNutritionSummary(
                    calorieConsumed = consumedCalories,
                    protein = consumedProtein,
                    carbs = consumedCarbs,
                    fat = consumedFat,
                    limitCalories = limitCalories,
                    limitProtein = limitProtein,
                    limitCarbs = limitCarbs,
                    limitFat = limitFat,
                    deviationPercent = deviation,
                    isInAlertRange = abs(deviation) >= 10.0
                )
            }
    }

    private fun parseGoal(rawGoal: String): GoalType =
        runCatching { GoalType.valueOf(rawGoal) }.getOrDefault(GoalType.MAINTAIN)

    private fun DietPlanEntity.toDomain(): DietPlan = DietPlan(
        userId = userId,
        goal = parseGoal(goal),
        caloriesTarget = caloriesTarget,
        proteinTarget = proteinTarget,
        carbTarget = carbTarget,
        fatTarget = fatTarget,
        updatedAtEpochMillis = updatedAtMillis
    )
}
