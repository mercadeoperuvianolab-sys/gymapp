package com.example.myapplication.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.GoalType
import com.example.myapplication.domain.model.MealEntry
import com.example.myapplication.domain.repository.SportGymRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: SportGymRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadCurrentDashboard()
    }

    fun loadCurrentDashboard() {
        viewModelScope.launch {
            val profile = repository.getCurrentUserProfile() ?: return@launch
            repository.observeDailyNutritionSummary(profile.id, LocalDate.now())
                .collectLatest { summary ->
                    _uiState.value = DashboardUiState(
                        userName = profile.name,
                        goal = profile.goal,
                        consumedCalories = summary.calorieConsumed,
                        targetCalories = summary.limitCalories,
                        protein = summary.protein,
                        carbs = summary.carbs,
                        fat = summary.fat,
                        alert = summary.isInAlertRange
                    )
                }
        }
    }

    fun addMeal(meal: MealEntry) {
        viewModelScope.launch {
            repository.addMeal(meal)
            loadCurrentDashboard()
        }
    }
}
