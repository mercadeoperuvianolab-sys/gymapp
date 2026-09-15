package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.DailyNutritionSummary
import com.example.myapplication.domain.repository.SportGymRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

class GetDailyNutritionSummaryUseCase(
    private val repository: SportGymRepository
) {
    operator fun invoke(userId: Long, date: LocalDate): Flow<DailyNutritionSummary> =
        repository.observeDailyNutritionSummary(userId, date)
}
