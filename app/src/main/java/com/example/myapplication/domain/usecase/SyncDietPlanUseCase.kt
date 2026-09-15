package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.DietPlan
import com.example.myapplication.domain.repository.SportGymRepository

class SyncDietPlanUseCase(
    private val repository: SportGymRepository
) {
    suspend operator fun invoke(plan: DietPlan): Long = repository.upsertDietPlan(plan)
}
